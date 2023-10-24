import edu.princeton.cs.algs4.Picture;
import edu.princeton.cs.algs4.StdOut;

public class SeamCarver {

    private Picture picture; // instance picture variable
    private int height; // instance variable to hold height of picture
    private int width; // instance variable to hold width of picture
    // instance 2d array variable to hold energy of all pixels in picture
    private double[][] energymatrix;


    /**
     * Constructor that takes the picture as input
     *
     * @param picture - picture to be input
     */
    public SeamCarver(Picture picture) {
        if (picture == null) {
            throw new IllegalArgumentException();
        }
        this.picture = picture;
        this.width = width();
        this.height = height();

    }


    /**
     * gets the picture
     *
     * @return the picture
     */
    public Picture picture() {
        return picture;
    }

    /**
     * gets the width of the picture
     *
     * @return an int value that is the width of the picture
     */
    public int width() {
        return picture.width();

    }

    /**
     * gets the height of the picture
     *
     * @return an int value that is the height of the picture
     */
    public int height() {
        return picture.height();

    }

    /**
     * finds the energy of a pixel, using the helper method
     *
     * @param x - x coordinate of the pixel
     * @param y - y coordinate of the pixel
     * @return a double value that is the energy of the given pixel
     */
    public double energy(int x, int y) {
        if (x < 0 || x >= width || y < 0 || y >= height) {
            throw new IllegalArgumentException();
        }
        double gradient = gradient(x, y);

        double energy = Math.sqrt(gradient);
        return energy;

    }

    /**
     * helper method to find the square of the energy of a pixel
     *
     * @param x - x coordinate of the pixel
     * @param y - y coordinate of the pixel
     * @return a double value that is the square of the energy of a pixel
     */
    private double gradient(int x, int y) {
        // Rx(x,y)2+Gx(x,y)2+Bx(x,y)2 , and where the central differences Rx(x,y),
        // Gx(x,y), and Bx(x,y) are the differences in the red, green, and
        // blue components between pixel (x + 1, y) and pixel (x − 1, y), respectively

        double xgradient = 0;
        double ygradient = 0;

        int xprev = x - 1;
        int xnext = x + 1;

        // handle wrapping around image
        if (x == 0) {
            xprev = width() - 1;
        }
        if (x == width() - 1) {
            xnext = 0;
        }
        if (xprev >= 0 && xnext < width()) { // within range
            int xprevcolor = picture.getRGB(xprev, y);
            int xnextcolor = picture.getRGB(xnext, y);
            // Color xprevcolor = picture.get(xprev, y);
            // Color xnextcolor = picture.get(xnext, y);


            double xblue = ((xprevcolor >> 0) & 0xFF) - ((xnextcolor >> 0) & 0xFF);
            double xred = ((xprevcolor >> 16) & 0xFF) - ((xnextcolor >> 16) & 0xFF);
            double xgreen = ((xprevcolor >> 8) & 0xFF) - ((xnextcolor >> 8) & 0xFF);
            xgradient = (Math.pow(xblue, 2)) + (Math.pow(xred, 2)) + (Math
                    .pow(xgreen, 2));
        }

        int yprev = y - 1;
        int ynext = y + 1;

        // handle wrapping around image
        if (y == 0) {
            yprev = height() - 1;
        }
        if (y == height() - 1) {
            ynext = 0;
        }
        if (yprev >= 0 && ynext < height()) { // within range
            int yprevcolor = picture.getRGB(x, yprev);
            int ynextcolor = picture.getRGB(x, ynext);
            // Color yprevcolor = picture.get(x, yprev);
            // Color ynextcolor = picture.get(x, ynext);

            double yblue = ((yprevcolor >> 0) & 0xFF) - ((ynextcolor >> 0) & 0xFF);
            double yred = ((yprevcolor >> 16) & 0xFF) - ((ynextcolor >> 16) & 0xFF);
            double ygreen = ((yprevcolor >> 8) & 0xFF) - ((ynextcolor >> 8) & 0xFF);
            ygradient = (Math.pow(yblue, 2)) + (Math.pow(yred, 2)) + (Math
                    .pow(ygreen, 2));
        }

        return xgradient + ygradient;


    }


    /**
     * finds the sequence of indices for the horizontal seam
     *
     * @return an int array of the indices for the horizontal seam
     */
    public int[] findHorizontalSeam() {
        transpose();
        int[] horizontalSeam = findVerticalSeam();
        transpose();
        return horizontalSeam;


    }

    /**
     * method to transpose a picture by switching the width and height
     */
    private void transpose() {
        Picture transpose = new Picture(picture.height(), picture.width());
        for (int i = 0; i < picture.width(); i++) {
            for (int j = 0; j < picture.height(); j++) {
                int piccolor = picture.getRGB(i, j);
                transpose.setRGB(j, i, piccolor);
            }
        }
        picture = transpose;
        int temporary = height;
        height = width;
        width = temporary;
    }

    /**
     * finds the sequence of indices for the vertical seam
     *
     * @return an int array of the indices for the vertical seam
     */
    public int[] findVerticalSeam() {

        energymatrix = new double[width()][height()];
        int[] vertseam = new int[height()];

        // convert picture to matrix of energy
        for (int row = 0; row < height(); row++) {
            for (int col = 0; col < width(); col++) {
                energymatrix[col][row] = energy(col, row);
            }
        }

        int[][] edgeTo = new int[width()][height()];
        double[][] distTo = new double[width()][height()];
        for (int row = 0; row < height(); row++) {
            for (int col = 0; col < width(); col++) {
                distTo[col][row] = Double.POSITIVE_INFINITY;
                if (row == 0) {
                    distTo[col][row] = energymatrix[col][row];
                }
            }
        }
        for (int row = 0; row < height() - 1; row++) {
            for (int col = 0; col < width(); col++) {
                // if value in distTo is greater than sum of distTo and energymatrix set that element
                // to the sum
                if (distTo[col][row + 1] > distTo[col][row] + energymatrix[col][row + 1]) {
                    distTo[col][row + 1] = distTo[col][row] + energymatrix[col][row + 1];
                    edgeTo[col][row + 1] = col;
                }
                // if the column - 1 is in range check the column before the current one
                if (col - 1 >= 0) {
                    if (distTo[col - 1][row + 1] > distTo[col][row] + energymatrix[col - 1][row
                            + 1]) {

                        distTo[col - 1][row + 1] = distTo[col][row] + energymatrix[col - 1][row
                                + 1];
                        edgeTo[col - 1][row + 1] = col;
                    }
                }
                // if the column + 1 is in range check the next column
                if (col + 1 < width()) {
                    if (distTo[col + 1][row + 1] > distTo[col][row] + energymatrix[col + 1][row
                            + 1]) {
                        distTo[col + 1][row + 1] = distTo[col][row] + energymatrix[col + 1][row
                                + 1];
                        edgeTo[col + 1][row + 1] = col;
                    }
                }
            }
        }
        /* StdOut.print("distTo: ");
        StdOut.println();
        for (int i = 0; i < distTo[0].length; i++) {
            for (int j = 0; j < distTo.length; j++) {
                StdOut.print(distTo[j][i] + " ");
            }
            StdOut.println();
        }
        StdOut.print("edgeTo: ");
        StdOut.println();
        for (int i = 0; i < edgeTo[0].length; i++) {
            for (int j = 0; j < edgeTo.length; j++) {
                StdOut.print(edgeTo[j][i] + " ");
            }
            StdOut.println();
        }
         */
        int mincol = 0;
        double minenergy = Double.POSITIVE_INFINITY;
        for (int col = 0; col < width(); col++) {
            // if the value in distTo is less than the current minenergy
            // set the min energy to the current value in distTo and
            // set the mincol to the current column.
            if (distTo[col][height() - 1] < minenergy) {
                minenergy = distTo[col][height() - 1];
                mincol = col;

            }
        }
        // StdOut.println("mincol " + mincol);
        // move through rows to reset mincol to be added to vertical seam
        int minrow = height() - 1;
        while (minrow >= 0) {
            vertseam[minrow] = mincol;
            mincol = edgeTo[mincol][minrow--];

        }
        /* StdOut.print("verticalseam: ");
        StdOut.println();
        for (int i = 0; i < verticalSeam.length; i++) {
            StdOut.print(verticalSeam[i] + " ");
        }
        StdOut.println(); */
        return vertseam;
    }

    /**
     * takes the sequence of indices for the horizontal seam and removes it from
     * the picture
     *
     * @param seam - array of the sequence of indices for the horizontal seam
     */
    public void removeHorizontalSeam(int[] seam) {
        // validate seam
        if (seam == null) {
            throw new IllegalArgumentException();
        }
        if (height() <= 1) {
            throw new IllegalArgumentException();
        }
        for (int entry = 0; entry < seam.length - 1; entry++) {
            if (Math.abs(seam[entry] - seam[entry + 1]) > 1) {
                throw new IllegalArgumentException();
            }
        }
        if (seam.length != width()) {
            throw new IllegalArgumentException();
        }

        transpose();
        removeVerticalSeam(seam);
        transpose();
    }

    /**
     * takes the sequence of indices for the vertical seam and removes it from
     * the picture
     *
     * @param seam - array of the sequence of indices for the vertical seam
     */
    public void removeVerticalSeam(int[] seam) {
        // validate seam
        if (seam == null) {
            throw new IllegalArgumentException();
        }
        if (width() <= 1) {
            throw new IllegalArgumentException();
        }
        for (int entry = 0; entry < seam.length - 1; entry++) {
            if (Math.abs(seam[entry] - seam[entry + 1]) > 1) {
                throw new IllegalArgumentException();
            }
        }
        if (seam.length != height()) {
            throw new IllegalArgumentException();
        }

        // new resized picture
        Picture resize = new Picture(width() - 1, height());
        for (int row = 0; row < height(); row++) {
            for (int col = 0; col < width() - 1; col++) {
                // if col is less than those in seam set it
                if (col < seam[row]) {
                    resize.setRGB(col, row, picture.getRGB(col, row));
                }
                else {
                    resize.setRGB(col, row, picture.getRGB(col + 1, row));
                }
            }
        }


        picture = new Picture(resize);

        /* // create energy matrix for new image
        energymatrix = new double[picture.width()][picture.height()];
        for (int col = 0; col < width(); col++) {
            for (int row = 0; row < height(); row++) {
                energymatrix[col][row] = energy(col, row);
            }
        } */
    }


    //  unit testing (required)
    public static void main(String[] args) {
        Picture picture = new Picture(args[0]);
        SeamCarver seamcarver = new SeamCarver(picture);
        int[] verticalseam = seamcarver.findVerticalSeam();
        int[] horizontalseam = seamcarver.findHorizontalSeam();
        StdOut.println("numrows to remove " + verticalseam.length);
        StdOut.println("numcols to remove " + horizontalseam.length);
        seamcarver.removeVerticalSeam(verticalseam);
        seamcarver.removeHorizontalSeam(horizontalseam);


    }

}
