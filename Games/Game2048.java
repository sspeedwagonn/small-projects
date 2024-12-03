package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

public class Game2048 extends JPanel {

    private enum State {
        START, WON, RUNNING, OVER
    }

    private static final int TARGET = 2048;
    private static final Color[] COLOR_TABLE = {
            new Color(0x701710), new Color(0xFFE4C3), new Color(0xFFF4D3),
            new Color(0xFFDAC3), new Color(0xE7B08E), new Color(0xE7BF8E),
            new Color(0xFFC4C3), new Color(0xE7948E), new Color(0xBE7E56),
            new Color(0xBE5E56), new Color(0x9C3931), new Color(0x701710)
    };

    private static int highest;
    private static int score;

    private final Color gridColor = new Color(0xBBADA0);
    private final Color emptyColor = new Color(0xCDC1B4);
    private final Color startColor = new Color(0xFFEBCD);
    private final Random random = new Random();
    private final int side = 4;
    private Tile[][] tiles;
    private State gameState = State.START;
    private boolean checkingAvailableMoves;

    public Game2048() {
        setPreferredSize(new Dimension(900, 700));
        setBackground(new Color(0xFAF8EF));
        setFont(new Font("SansSerif", Font.BOLD, 48));
        setFocusable(true);
        initializeListeners();
    }

    private void initializeListeners() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                startGame();
                repaint();
            }
        });

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (gameState == State.RUNNING) {
                    switch (e.getKeyCode()) {
                        case KeyEvent.VK_UP -> moveUp();
                        case KeyEvent.VK_DOWN -> moveDown();
                        case KeyEvent.VK_LEFT -> moveLeft();
                        case KeyEvent.VK_RIGHT -> moveRight();
                    }
                    repaint();
                }
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        drawGrid(g2d);
    }

    private void startGame() {
        if (gameState != State.RUNNING) {
            score = 0;
            highest = 0;
            gameState = State.RUNNING;
            tiles = new Tile[side][side];
            addRandomTile();
            addRandomTile();
        }
    }

    private void drawGrid(Graphics2D g) {
        g.setColor(gridColor);
        g.fillRoundRect(200, 100, 499, 499, 15, 15);

        if (gameState == State.RUNNING) {
            for (int r = 0; r < side; r++) {
                for (int c = 0; c < side; c++) {
                    if (tiles[r][c] == null) {
                        g.setColor(emptyColor);
                        g.fillRoundRect(215 + c * 121, 115 + r * 121, 106, 106, 7, 7);
                    } else {
                        drawTile(g, r, c);
                    }
                }
            }
        } else {
            drawStartOrEndScreen(g);
        }
    }

    private void drawStartOrEndScreen(Graphics2D g) {
        g.setColor(startColor);
        g.fillRoundRect(215, 115, 469, 469, 7, 7);

        g.setColor(gridColor.darker());
        g.setFont(new Font("SansSerif", Font.BOLD, 128));
        g.drawString("2048", 310, 270);

        g.setFont(new Font("SansSerif", Font.BOLD, 20));

        String message = switch (gameState) {
            case WON -> "You made it!";
            case OVER -> "Game over";
            default -> "Click to start a new game";
        };

        g.drawString(message, 390, 350);
        g.setColor(gridColor);
        g.drawString("Click to start a new game", 330, 470);
        g.drawString("(Use arrow keys to move tiles)", 310, 530);
    }

    private void drawTile(Graphics2D g, int r, int c) {
        int value = tiles[r][c].getValue();
        g.setColor(COLOR_TABLE[(int) (Math.log(value) / Math.log(2)) + 1]);
        g.fillRoundRect(215 + c * 121, 115 + r * 121, 106, 106, 7, 7);

        g.setColor(value < 128 ? COLOR_TABLE[0] : COLOR_TABLE[1]);
        drawCenteredString(g, String.valueOf(value), new Rectangle(215 + c * 121, 115 + r * 121, 106, 106));
    }

    private void drawCenteredString(Graphics2D g, String text, Rectangle rect) {
        FontMetrics fm = g.getFontMetrics();
        int x = rect.x + (rect.width - fm.stringWidth(text)) / 2;
        int y = rect.y + (rect.height - fm.getHeight()) / 2 + fm.getAscent();
        g.drawString(text, x, y);
    }

    private void addRandomTile() {
        int pos;
        int row, col;
        do {
            pos = random.nextInt(side * side);
            row = pos / side;
            col = pos % side;
        } while (tiles[row][col] != null);

        tiles[row][col] = new Tile(random.nextInt(10) == 0 ? 4 : 2);
    }

    private boolean move(int countDownFrom, int yIncr, int xIncr) {
        boolean moved = false;

        for (int i = 0; i < side * side; i++) {
            int j = Math.abs(countDownFrom - i);
            int r = j / side;
            int c = j % side;

            if (tiles[r][c] == null) continue;

            int nextR = r + yIncr;
            int nextC = c + xIncr;

            while (isInBounds(nextR, nextC)) {
                Tile next = tiles[nextR][nextC];
                Tile curr = tiles[r][c];

                if (next == null) {
                    if (checkingAvailableMoves) return true;

                    tiles[nextR][nextC] = curr;
                    tiles[r][c] = null;
                    r = nextR;
                    c = nextC;
                    nextR += yIncr;
                    nextC += xIncr;
                    moved = true;
                } else if (next.canMergeWith(curr)) {
                    if (checkingAvailableMoves) return true;

                    int value = next.mergeWith(curr);
                    if (value > highest) highest = value;
                    score += value;
                    tiles[r][c] = null;
                    moved = true;
                    break;
                } else {
                    break;
                }
            }
        }

        if (moved) {
            handleMoveOutcome();
        }

        return moved;
    }

    private void handleMoveOutcome() {
        if (highest < TARGET) {
            clearMerged();
            addRandomTile();
            if (!movesAvailable()) {
                gameState = State.OVER;
            }
        } else if (highest == TARGET) {
            gameState = State.WON;
        }
    }

    private boolean isInBounds(int row, int col) {
        return row >= 0 && row < side && col >= 0 && col < side;
    }

    private void clearMerged() {
        for (Tile[] row : tiles) {
            for (Tile tile : row) {
                if (tile != null) tile.setMerged(false);
            }
        }
    }

    private boolean movesAvailable() {
        checkingAvailableMoves = true;
        boolean hasMoves = moveUp() || moveDown() || moveLeft() || moveRight();
        checkingAvailableMoves = false;
        return hasMoves;
    }

    private boolean moveUp() {
        return move(0, -1, 0);
    }

    private boolean moveDown() {
        return move(side * side - 1, 1, 0);
    }

    private boolean moveLeft() {
        return move(0, 0, -1);
    }

    private boolean moveRight() {
        return move(side * side - 1, 0, 1);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setTitle("2048");
            frame.setResizable(false);
            frame.add(new Game2048(), BorderLayout.CENTER);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}

class Tile {
    private boolean merged;
    private int value;

    Tile(int value) {
        this.value = value;
    }

    int getValue() {
        return value;
    }

    void setMerged(boolean merged) {
        this.merged = merged;
    }

    boolean canMergeWith(Tile other) {
        return !merged && other != null && !other.merged && value == other.value;
    }

    int mergeWith(Tile other) {
        if (canMergeWith(other)) {
            value *= 2;
            merged = true;
            return value;
        }
        return -1;
    }
}
