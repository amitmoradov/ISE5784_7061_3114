package renderer;

/**
 * Pixel class is a helper class for managing the progress of the rendering process.
 * It is used to print the progress percentage, and to get the next pixel to be rendered.
 */
record Pixel(int row, int col) {
    private static int maxRows = 0; // number of rows (height) of the image
    private static int maxCols = 0; // number of columns (width) of the image
    private static long totalPixels = 0l; // total number of pixels in the image
    private static volatile int cRow = 0; // current row number
    private static volatile int cCol = -1; // current column number
    private static volatile long pixels = 0l; // number of pixels that have been rendered
    private static volatile int lastPrinted = 0; // the last progress percentage that was printed
    private static boolean print = false; // indicator for printing the progress percentage
    private static long printInterval = 100l; // the interval for printing the progress percentage
    private static final String PRINT_FORMAT = "%5.1f%%\r"; // the format for printing the progress percentage
    private static Object mutexNext = new Object(); // mutex for the next pixel
    private static Object mutexPixels = new Object(); // mutex for the pixels counter

    /**
     * Initializes the Pixel class with the image dimensions and the interval for printing the progress percentage.
     *
     * @param maxRows   number of rows (height) of the image
     * @param maxCols   number of columns (width) of the image
     * @param interval  the interval for printing the progress percentage
     */
    static void initialize(int maxRows, int maxCols, double interval) {
        Pixel.maxRows = maxRows;
        Pixel.maxCols = maxCols;
        Pixel.totalPixels = (long) maxRows * maxCols;
        printInterval = (int) (interval * 10);
        // print the first progress percentage
        if (print = printInterval != 0) System.out.printf(PRINT_FORMAT, 0d);
    }

    /**
     * Gets the next pixel to be rendered.
     *
     * @return the next pixel to be rendered, or null if all the pixels have been rendered
     */
    static Pixel nextPixel() {
        synchronized (mutexNext) {
            if (cRow == maxRows) return null;
            ++cCol;
            // check if the next pixel is in the image boundaries
            if (cCol < maxCols) return new Pixel(cRow, cCol);
            cCol = 0;
            ++cRow;
            if (cRow < maxRows) return new Pixel(cRow, cCol);
        }
        return null;
    }

    /**
     * Updates the progress percentage and prints it.
     * This function is called by each thread after rendering a pixel.
     */
    static void pixelDone() {
        boolean flag = false;
        int percentage = 0;
        synchronized (mutexPixels) {
            ++pixels;
            if (print) {
                // Calculate the progress percentage
                percentage = (int) (1000l * pixels / totalPixels);
                if (percentage - lastPrinted >= printInterval) {
                    lastPrinted = percentage;
                    flag = true;
                }
            }
        }
        if (flag) System.out.printf(PRINT_FORMAT, percentage / 10d);
    }

//    /**
//     * Internal function for thread-safe manipulating of main follow up Pixel object - this function is
//     * critical section for all the threads, and main Pixel object data is the shared data of this critical* section.<br/>
//     * The function provides next pixel number each call.
//     *
//     * @param target target secondary Pixel object to copy the row/column of the next pixel
//     * @return the progress percentage for follow up: if it is 0 - nothing to print, if it is -1 - the task is* finished, any other value - the progress percentage (only when it changes)
//     */
//    private synchronized int nextP(Pixel target) {
//        ++col;
//        ++counter;
//        if (col < maxCols) {
//            target.row = this.row;
//            target.col = this.col;
//            if (print && counter == nextCounter) {
//                ++percents;
//                nextCounter = pixels * (percents + 1) / 100;
//                return percents;
//            }
//            return 0;
//        }
//        ++row;
//        if (row < maxRows) {
//            col = 0;
//            if (print && counter == nextCounter) {
//                ++percents;
//                nextCounter = pixels * (percents + 1) / 100;
//                return percents;
//            }
//            return 0;
//        }
//        return -1;
//    }
}
