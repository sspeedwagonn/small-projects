diceRoll = function(sides)
    print ceil(rnd * sides)
end function

inp = input("How many sides should the dice have?").val
diceRoll(inp)