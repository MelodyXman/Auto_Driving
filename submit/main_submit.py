### Main point ---
### Find Shortest steps from end point, mark each point's step; check if can reach the End point, if not, print None.
### Then go from E to S to find all possible shortest paths.

import random
import numpy as np
import find_path as sol

def getRandomStart(map_r):
    x = random.randint(0, len(map_r) - 1)
    y = random.randint(0, len(map_r[0]) - 1)
    map_r[x][y] = 'S'
    sx = x #start point's coordinate
    sy = y

    while(map_r[x][y] != 'E'):
        x = random.randint(0, len(map_r) - 1)
        y = random.randint(0, len(map_r[0]) - 1)
        if(map_r[x][y] == 'S'):
            continue
        map_r[x][y] = 'E'

    print(map_r)
    return sx,sy



if __name__ == '__main__':
# test -- valid path

    map = np.array([
                ['S', 'X', ' ',' ', ' '  , 'X', 'X', 'E', ' ', 'X'],
                [' ', 'X', 'X',' ', ' '  , 'X', 'X', ' ', 'X', 'X'],
                [' ', 'X', 'X',' ', ' '  , ' ', 'X', ' ', ' ', 'X'],
                [' ', ' ', 'X',' ', ' '  , 'X', 'X', ' ', 'X', 'X'],
                [' ', ' ', ' ',' ', 'X'  , 'X', ' ', ' ', ' ', 'X'],
                [' ', ' ', ' ',' ', 'X'  , 'X', ' ', ' ', ' ', 'X'],

                [' ', ' ', ' ',' ', ' '  , ' ', ' ', ' ', ' ', ' '],
                ['X', ' ', ' ',' ', ' '  , ' ', ' ', ' ', ' ', ' '],
                ['X', 'X', 'X',' ', ' '  , ' ', ' ', ' ', 'X', 'X']
    ])

    print("test1 -- given map")
    py_map = np.char.replace(map,' ','.')
    print(py_map)
    s = sol.Solution2(py_map, 0, 0)
    s.engineOn()

#test -- invalid path
    print()
    map_noRes = np.array([
                ['S', '.', '.','X'],
                ['.', '.', 'X','.'],
                ['.', 'X', '.','.'],
                ['.', '.', 'X','E']
    ])
    print("test2 -- invalid path")
    print(map_noRes)
    s = sol.Solution2(map_noRes, 0, 0)
    s.engineOn()

#test - only path
    print()
    map_one = np.array([
                ['S', '.', '.','E'],
    ])
    print("test3 -- only path")
    print(map_one)
    s = sol.Solution2(map_one, 0, 0)
    s.engineOn()

#test - random
    print()
    map = np.array([
                [' ', 'X', ' ',' ', ' '  , 'X', 'X', ' ', ' ', 'X'],
                [' ', 'X', 'X',' ', ' '  , 'X', 'X', ' ', 'X', 'X'],
                [' ', 'X', 'X',' ', ' '  , ' ', 'X', ' ', ' ', 'X'],
                [' ', ' ', 'X',' ', ' '  , 'X', 'X', ' ', 'X', 'X'],
                [' ', ' ', ' ',' ', 'X'  , 'X', ' ', ' ', ' ', 'X'],
                [' ', ' ', ' ',' ', 'X'  , 'X', ' ', ' ', ' ', 'X'],
                [' ', ' ', ' ',' ', ' '  , ' ', ' ', ' ', ' ', ' '],
                ['X', ' ', ' ',' ', ' '  , ' ', ' ', ' ', ' ', ' '],
                ['X', 'X', 'X',' ', ' '  , ' ', ' ', ' ', 'X', 'X']
    ])

    print("test4 -- random start,end in map")
    py_map = np.char.replace(map,' ', '.')
    sx,sy = getRandomStart(py_map)

    s = sol.Solution2(py_map, sx, sy)
    s.engineOn()
