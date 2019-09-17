package oa.oa_gongsi.xiaocheche;

import java.util.*;

// 手动实现489. Robot Room Cleaner （探索型car）
// 参考286. Walls and Gates （计划路线型car， ）
class xiaocheche {
    private static final int[][] directions = {{1, 0}, {0, 1}, {-1, 0}, {0, -1}};
    static class Car {
        int x = 0; //cur location of the car
        int y = 0;

        private int index = 0;
        char[][] map;

        public Car(char[][] map) {
            this.map = map; //shallow copy here
        }

        public void walk(){
            if(!canMove())
                return;
            x += directions[index][0];
            y += directions[index][1];
        }

        public void turnRight(){
            if(index == 0) index = 3;
            else index--;
        }

        public void turnLeft(){
            index++;
            index %= 4;
//            if(index == 3)  index = 0;
//            else index++;
        }

        public boolean canMove() {
            int next_x = x + directions[index][0];
            int next_y = y + directions[index][1];

            if(next_x >= map.length || next_x < 0 || next_y == map.length || next_y < 0)
                return false;

            return map[next_x ][next_y] != 'X';
        }

        public boolean findDestination(){
            if(map[x][y] == 'E')
                System.out.println("bingo");

            return map[x][y] == 'E';
        }
    }

    public static void main(String[] args){
        char[][] map = new char[][] {

                {'S', 'X', ' ',' ', ' '  , 'X', 'X', 'E', ' ', 'X'},
                {' ', 'X', 'X',' ', ' '  , 'X', 'X', ' ', 'X', 'X'},
                {' ', 'X', 'X',' ', ' '  , ' ', 'X', ' ', ' ', 'X'},
                {' ', ' ', 'X',' ', ' '  , 'X', 'X', ' ', 'X', 'X'},
                {' ', ' ', ' ',' ', 'X'  , 'X', ' ', ' ', ' ', 'X'},
                {' ', ' ', ' ',' ', 'X'  , 'X', ' ', ' ', ' ', 'X'},

                {' ', ' ', ' ',' ', ' '  , ' ', ' ', ' ', ' ', ' '},
                {'X', ' ', ' ',' ', ' '  , ' ', ' ', ' ', ' ', ' '},
                {'X', 'X', 'X',' ', ' '  , ' ', ' ', ' ', 'X', 'X'}
        };

        char[][] map_non = new char[][] {

                {'S', ' ', ' ','X'},
                {' ', 'X', 'X',' '},
                {' ', 'X', 'X',' '},
                {' ', ' ', 'X','E'}
        };


////test - V:
//        Car myCar = new Car(map);
//        Solution1 s1= new Solution1(); //discover type car
//        s1.engineOn(myCar);
////test - F:
//        Car myCar12 = new Car(map_non);
//        Solution1 s12= new Solution1(); //discover type car
//        s12.engineOn(myCar12);

////F2
//
        Car myCar21 = new Car(map);
        Solution2 s21 = new Solution2();
        s21.engineOn(myCar21);

        System.out.println("-----");
        Car myCar22 = new Car(map_non);
        Solution2 s22 = new Solution2();
        s22.engineOn(myCar22);
        System.out.println("-----");

        char[][] map2 = new char[][] {

                {'S', ' ', ' ','X'},
                {' ', ' ', 'X',' '},
                {'X', ' ', 'X',' '},
                {' ', ' ', ' ','E'}
        };

        Car myCar23 = new Car(map2);
        Solution2 s23 = new Solution2();
        s23.engineOn(myCar23);


    }

    static class Solution2 {
        public void engineOn(Car car) {  //Car is the dependency Inversion, in walk(), will call Car's walk()
            boolean reachEnd = false;
            int[] max = new int[1];

            //S at[0,0]
            char[][] map = new char[car.map.length][car.map[0].length];
            for(int i = 0; i < car.map.length; i++){
                for(int j = 0; j < car.map[0].length; j++) {
                    map[i][j] = car.map[i][j];
                }
            }

            int[][] stepMap = new int[map.length][map[0].length];

            if(findPath(map, stepMap,0, 0, 0, max))
                reachEnd = true;

            for(char[] m : map)
                System.out.println(Arrays.toString(m));

            car.map = map; //reset map to original input
            for(char[] m : map)
                System.out.println(Arrays.toString(m));

            List<List<String>> res = new ArrayList<>();
            List<String> path = new ArrayList<>();

            if(!reachEnd) {
                System.out.println("opps..");
                path.add("none");
                res.add(path);
            } else {

                for(int i = 0 ; i < car.map.length; i++) {
                    for (int j = 0; j < car.map[0].length; j++) {
                        if (car.map[i][j] == 'E') {
                            getShortestPath(stepMap, i, j, max[0], path, res);
                            break;
                        }
                    }
                }
                // todo
                // go();
            }

            System.out.println(res);
        }


        private static boolean findPath(char[][] map, int[][] stepMap, int x, int y, int step, int[] max) {
            if (x == map.length || x < 0 || y == map[0].length || y < 0)
                return false;

            if(map[x][y] == 'E') {
                max[0] = step;
                return true;
            }
            if (map[x][y] == 'X' || map[x][y] == '*')
                return false;

            //System.out.println("at: " +x +","+y + ":" + (map[x][y]- '0'));
            if(map[x][y] != ' ') {
                if (map[x][y] == 'S') {
                    //
                } else if(stepMap[x][y] > step)
                    return false;
            }
            char c = map[x][y]; //visited
            map[x][y] = '*';
            stepMap[x][y] = step;

            //坑----- 这里x+1,x-1的顺序要分开，不能连在一起写！！！！！！
            return findPath(map, stepMap,x + 1, y, step + 1, max)
                    || findPath(map, stepMap,  x, y+1, step + 1, max)
                    || findPath(map, stepMap, x - 1, y, step + 1, max)
                    || findPath(map, stepMap, x, y-1, step + 1, max);

        }

        public void getShortestPath(int[][] map, int x, int y, int curStep, List<String>path, List<List<String>> res) {

            path.add("[" + x + " " + y + "]");

            if(x == 0 && y == 0) {
                res.add(new ArrayList<>(path));
                return;
            }

            if(x+1 < map.length && map[x+1][y] == curStep-1)
                getShortestPath(map, x+1, y, curStep-1, path, res);
            if(x-1 >= 0 && map[x-1][y] == curStep-1)
                getShortestPath(map, x-1, y, curStep-1, path, res);

            if(y+1 < map[0].length && map[x][y+1] == curStep-1)
                getShortestPath(map, x, y+1, curStep-1, path, res);
            if(y-1 >= 0 && map[x][y-1] == curStep-1)
                getShortestPath(map, x, y-1, curStep-1, path, res);


            path.remove(path.size() - 1);
        }

    }

    static class Solution1 {
        public void engineOn(Car car) {  //car is the invert dependent, in walk(), will call Car's walk()
            Set<String> visited = new HashSet<>();
            List<String> path = new ArrayList<>();

            //S at [0][0]
            if(!go(car, 0, 0, 0, visited, path)) {
                path = new ArrayList<>();
                path.add("none");
            }
            System.out.println(path);

        }

        private static boolean go(Car car, int x, int y, int curDirection, Set<String> visited, List<String> path) {
            // Clean current cell.
            car.walk();
            visited.add(x + " " + y);

            if (car.findDestination())
                return true;
            path.add("[" + x + ' ' + y + "]");

            for (int i = 0; i < 4; i++) {
                //System.out.println("keep direction: " + curDirection);
                int nx = x + directions[curDirection][0];
                int ny = y + directions[curDirection][1];

                if (!visited.contains(nx + " " + ny) && car.canMove()) {
                    if (go(car, nx, ny, curDirection, visited, path))
                        return true;
                }

                // Change orientation.
                car.turnLeft();
                curDirection++;
                curDirection %= 4;
                //System.out.println("now turn to direction: " + curDirection);
            }

            // Move backward one step while maintaining the orientation.
            moveBack(car);
            // System.out.println("now 退回一格");
            return false;
        }

        private static void moveBack(Car car) {
            car.turnRight();
            car.turnRight();
            car.walk();
            car.turnRight();
            car.turnRight();
        }
    }
}
