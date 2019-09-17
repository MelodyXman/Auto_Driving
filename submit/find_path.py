import collections
import copy
import numpy as np

# Find E point in map from S(here we assume S=(0,0), otherwise pass start coordinate as parameter) , if there is a path, printout
# ‘X’: obstacle
# ‘S’: start position
# ‘E’: end position
# ‘.’: drivable space

class Solution2:
    directions = [(-1, 0), (0, 1),(1, 0),(0, -1)] # U R, D, L
    def __init__ (self, map, start_x, start_y):
        self.sx = start_x
        self.sy = start_y

        self.m = len(map)
        self.n = len(map[0])
        self.map = copy.deepcopy(map)

        self.stepMap = np.zeros([self.m, self.n])
        self.maxStep = 0
        self.result = []
        self.hasPath = False;

    def engineOn(self):
        self.hasPath = self.findPath()
        print(self.hasPath, "From",(self.sx,self.sy)," to ",'E')
        #print(self.stepMap)
        if not self.hasPath:
            print(None)
            return

        resList = self.getPaths()

        for l in resList:
            l.reverse()

        print("Possible shortest paths: ",len(resList))
        print("Length of shortest path: ", len(resList[0]), "steps")
        print("Shortest path:", resList[0])

    def findPath(self):
        m = self.m
        n = self.n

        queue = collections.deque()
        queue.append((self.sx, self.sy, 0))

        while queue:
            r, c, step = queue.popleft()
            self.map[r][c] = '*' # visited

            for i, j in [(-1, 0), (0, 1),(1, 0),(0, -1)]:
                nx = r + i
                ny = c + j

                if nx == m or nx < 0 or ny == n or ny < 0:
                    continue
                if self.map[nx][ny] == 'X' or self.map[nx][ny] == '*':
                    continue

                if self.map[nx][ny] == 'E':
                    self.stepMap[nx][ny] = step + 1
                    self.maxStep = step + 1
                    return True

                if self.map[nx][ny] == '.' or self.stepMap[nx][ny] > step+1:
                    self.stepMap[nx][ny] = step+1
                    queue.append((nx, ny, step+1))

        return False

    #Get all paths, starts from the shortest path (walk from E to S)
    def getPaths(self):
        for i in range(self.m):
            for j in range(self.n):
                if self.map[i][j] == 'E':
                    dfs(self.stepMap, i, j, self.maxStep, [])
                    break

                def dfs(map, x ,y , curStep, path):
                    path.append((x,y))
                    #print (path)
                    if x == self.sx and y == self.sy:
                        self.result.append(copy.deepcopy(path))
                        return

                    if x+1 < len(map) and map[x+1][y] == curStep-1:
                        dfs(map, x+1, y, curStep-1, path)

                    if y+1 < len(map[0]) and map[x][y+1] == curStep-1:
                        dfs(map, x, y+1, curStep-1, path)

                    if x-1 >= 0 and map[x-1][y] == curStep-1:
                        dfs(map, x-1, y, curStep-1, path)

                    if y-1 >= 0 and map[x][y-1] == curStep-1:
                        dfs(map, x, y-1, curStep-1, path)

                    path.pop(-1)
                    return

        return self.result
