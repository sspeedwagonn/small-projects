--[[
  This is my first Lua program! It takes an integer from 1-10 and compares it against the random number. If you get it, you win! If you don't, keep guessing until you do!
]]

math.randomseed(os.time())
local number = math.random(1, 10)
local guess = nil

print("Guess a number from 1-10!")

while true do
  print("Enter guess:")
  guess = tonumber(io.read())

  if not guess then
    print("Invalid input! Please enter a number between 1 and 10.")
  elseif guess < 1 or guess > 10 then
    print("Out of range! Please guess a number between 1 and 10.")
  elseif guess == number then
    print("Correct! You guessed the number!")
    break
  else
    print("Wrong number! Try again.")
  end
end

print("Thanks for playing!")
