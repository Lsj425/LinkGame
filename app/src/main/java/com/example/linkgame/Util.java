package com.example.linkgame;

import android.util.Log;

public class Util {
    /**
     * 判断两点是否可以连接
     *
     * @param map
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @return
     */
    public static boolean linkable(int[][] map, int x1, int y1, int x2, int y2) {
        if (x1 == x2 && y1 == y2) return false;

        int row = map.length - 1, column = map[0].length - 1;
        Log.d("linkable", "row:" + row + " column:" + column);

        //上边界
        if ((x1 == 0 || vertical(map, x1, y1, -1, y1)) &&
                (x2 == 0 || vertical(map, x2, y2, -1, y2))) {
            return true;
        }

        //下边界
        if ((x1 == map.length - 1 || vertical(map, x1, y1, map.length, y1)) &&
                (x2 == map.length - 1 || vertical(map, x2, y2, map.length, y2))) {
            return true;
        }

        //左边界
        if ((y1 == 0 || horizon(map, x1, y1, x1, -1)) &&
                (y2 == 0 || horizon(map, x2, y2, x2, -1))) {
            return true;
        }

        //右边界
        if ((y1 == map[0].length - 1 || horizon(map, x1, y1, x1, map[0].length)) &&
                (y2 == map[0].length - 1 || horizon(map, x2, y2, x2, map[0].length))) {
            return true;
        }

        Log.d("linkable", "inner");

        if (horizon(map, x1, y1, x2, y2)) {
            return true;
        }

        if (vertical(map, x1, y1, x2, y2)) {
            return true;
        }

        if (turnOnce(map, x1, y1, x2, y2)) {
            return true;
        }

        if (turnTwice(map, x1, y1, x2, y2)) {
            return true;
        }
        return false;
    }

    /**
     * 判断该位置是否被挡住
     *
     * @param map
     * @param x
     * @param y
     * @return
     */
    public static boolean isBlocked(int[][] map, int x, int y) {
        if (map[x][y] == 0) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 判断水平方向
     *
     * @param map
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @return
     */
    public static boolean horizon(int[][] map, int x1, int y1, int x2, int y2) {
        if (x1 == x2 && y1 == y2) {
            return false;
        }

        if (x1 != x2) {
            return false;
        }

        int startY = Math.min(y1, y2);
        int endY = Math.max(y1, y2);

        if (startY + 1 == endY) return true;

        for (int j = startY + 1; j < endY; j++) {
            if (isBlocked(map, x1, j)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断垂直方向
     *
     * @param map
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @return
     */
    public static boolean vertical(int[][] map, int x1, int y1, int x2, int y2) {
        if (x1 == x2 && y1 == y2) {
            return false;
        }

        if (y1 != y2) {
            return false;
        }

        int startX = Math.min(x1, x2);
        int endX = Math.max(x1, x2);

        if (startX + 1 == endX) return true;

        for (int i = startX + 1; i < endX; i++) {
            if (isBlocked(map, i, y1)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断一个拐点的路径
     *
     * @param map
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @return
     */
    public static boolean turnOnce(int[][] map, int x1, int y1, int x2, int y2) {
        if (x1 == x2 && y1 == y2) {
            return false;
        }
        int c_x = x1, c_y = y2;
        int d_x = x2, d_y = y1;

        if (!isBlocked(map, c_x, c_y)) {
            if (horizon(map, x1, y1, c_x, c_y) && vertical(map, c_x, c_y, x2, y2)) {
                return true;
            }
        }
        if (!isBlocked(map, d_x, d_y)) {
            if (horizon(map, x1, y1, d_x, d_y) && vertical(map, d_x, d_y, x2, y2))
                return true;
        }
        return false;
    }

    /**
     * 判断两个拐点的路径
     *
     * @param map
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @return
     */
    public static boolean turnTwice(int[][] map, int x1, int y1, int x2, int y2) {
        if (x1 == x2 && y1 == y2) {
            return false;
        }

        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                if (i != x1 && i != x2 && j != y1 && j != y2) {
                    continue;
                }

                if ((i == x1 && j == y1) || (i == x2 && j == y2)) {
                    continue;
                }

                if (isBlocked(map, i, j)) {
                    continue;
                }
                if (turnOnce(map, x1, y1, i, j) && (horizon(map, i, j, x2, y2) || vertical(map, i, j, x2, y2))) {
                    return true;
                }
                if (turnOnce(map, i, j, x2, y2) && (horizon(map, x1, y1, i, j) || vertical(map, x1, y1, i, j))) {
                    return true;
                }
            }
        }
        return false;
    }
}


