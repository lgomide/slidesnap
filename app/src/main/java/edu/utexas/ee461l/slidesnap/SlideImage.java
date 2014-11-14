package edu.utexas.ee461l.slidesnap;

/**
 * Created by Thomas on 11/14/2014.
 */
import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import java.awt.*;

public class SlideImage {

    int newRow = 0;
    int newColumn = 0;

    // The SlideImage constructor will create an object that will hold
    // the possible coordinates for where the the blank image can be moved
    public SlideImage(int newRow, int newColumn) {
        this.newRow = newRow;
        this.newColumn = newColumn;
    }

    public static void SplitImage() throws IOException {

        int newOrder[][] = new int[3][3];
        File file = new File("catbeer.jpg"); // This will need to be changed later
        FileInputStream fis = new FileInputStream(file);
        BufferedImage image = ImageIO.read(fis); // reading the image file

        int rows = 3;
        int cols = 3;
        int chunks = rows * cols;

        int chunkWidth = image.getWidth() / cols; // determines the chunk width
        // and height
        int chunkHeight = image.getHeight() / rows;
        int count = 0;
        BufferedImage imgs[] = new BufferedImage[chunks]; // Image array to hold image chunks
        BufferedImage finalImgs[] = new BufferedImage[chunks]; // Final image chunks

        // This loop takes the picture and breaks it up into the number
        // of chunks based on the rows and cols number specified
        for (int x = 0; x < rows; x++) {
            for (int y = 0; y < cols; y++) {
                // Initialize the image array with image chunks
                imgs[count] = new BufferedImage(chunkWidth, chunkHeight,
                        image.getType());

                // The if else command will draw the image chunk
                if(x == (rows - 1) && y == (cols - 1)){ // If last puzzle piece, make it black
                    Graphics2D gr = imgs[count++].createGraphics();
                    for(int a = 0; a < image.getWidth(); a++){
                        for(int b = 0; b < image.getHeight(); b++){
                            image.setRGB(a, b, 0x00000000);
                        }
                    }
                    gr.drawImage(image, 0, 0, chunkWidth, chunkHeight, chunkWidth
                                    * y, chunkHeight * x, chunkWidth * y + chunkWidth,
                            chunkHeight * x + chunkHeight, null);
                    gr.dispose();
                }else{ // Not last puzzle piece, draw normally
                    Graphics2D gr = imgs[count++].createGraphics();
                    gr.drawImage(image, 0, 0, chunkWidth, chunkHeight, chunkWidth
                                    * y, chunkHeight * x, chunkWidth * y + chunkWidth,
                            chunkHeight * x + chunkHeight, null);
                    gr.dispose();
                }
            }
        }

        // Gets a random order for the picture
        newOrder = PuzzleJumbler();

        // Rearranges the picture based on the new ordering
        finalImgs[0] = imgs[newOrder[0][0]];
        finalImgs[1] = imgs[newOrder[0][1]];
        finalImgs[2] = imgs[newOrder[0][2]];
        finalImgs[3] = imgs[newOrder[1][0]];
        finalImgs[4] = imgs[newOrder[1][1]];
        finalImgs[5] = imgs[newOrder[1][2]];
        finalImgs[6] = imgs[newOrder[2][0]];
        finalImgs[7] = imgs[newOrder[2][1]];
        finalImgs[8] = imgs[newOrder[2][2]];

        // writing mini images into image files
        for (int i = 0; i < finalImgs.length; i++) {
            ImageIO.write(finalImgs[i], "jpg", new File("img" + i + ".jpg"));
        }
    }

    // This method will return a 2d array that has the random image ordering
    public static int[][] PuzzleJumbler() {
        Random rand = new Random();
        // This arraylist will have all of the possible new coordinates
        ArrayList<SlideImage> choices = new ArrayList<SlideImage>();
        int puzzleArray[][] = new int[3][3];
        int temp = -1;

        // Each unit is given a number
        puzzleArray[0][0] = 0;
        puzzleArray[0][1] = 1;
        puzzleArray[0][2] = 2;
        puzzleArray[1][0] = 3;
        puzzleArray[1][1] = 4;
        puzzleArray[1][2] = 5;
        puzzleArray[2][0] = 6;
        puzzleArray[2][1] = 7;
        puzzleArray[2][2] = 8;

        // The row and column will always have the
        // coordinates to where the blank space is
        int row = 2;
        int column = 2;

        // Based on the row and column this code will add all
        // of the possible new coordinates that the blank
        // space could possibly move into the ArrayList choices
        // Mixes up the photo 10 times
        for (int x = 0; x < 10; x++) {
            if (row == 0) {
                if (column == 0) {
                    choices.add(new SlideImage((row), (column + 1)));
                    choices.add(new SlideImage((row + 1), (column)));
                }
                if (column == 1) {
                    choices.add(new SlideImage((row), (column - 1)));
                    choices.add(new SlideImage((row + 1), (column)));
                    choices.add(new SlideImage((row), (column + 1)));
                }
                if (column == 2) {
                    choices.add(new SlideImage((row), (column - 1)));
                    choices.add(new SlideImage((row + 1), (column)));
                }
            } else if (row == 1) {
                if (column == 0) {
                    choices.add(new SlideImage((row - 1), (column)));
                    choices.add(new SlideImage((row), (column + 1)));
                    choices.add(new SlideImage((row + 1), (column)));
                }
                if (column == 1) {
                    choices.add(new SlideImage((row - 1), (column)));
                    choices.add(new SlideImage((row), (column - 1)));
                    choices.add(new SlideImage((row + 1), (column)));
                    choices.add(new SlideImage((row), (column + 1)));
                }
                if (column == 2) {
                    choices.add(new SlideImage((row - 1), (column)));
                    choices.add(new SlideImage((row), (column - 1)));
                    choices.add(new SlideImage((row + 1), (column)));
                }
            } else {
                if (column == 0) {
                    choices.add(new SlideImage((row - 1), (column)));
                    choices.add(new SlideImage((row), (column + 1)));
                }
                if (column == 1) {
                    choices.add(new SlideImage((row - 1), (column)));
                    choices.add(new SlideImage((row), (column - 1)));
                    choices.add(new SlideImage((row), (column + 1)));
                }
                if (column == 2) {
                    choices.add(new SlideImage((row), (column - 1)));
                    choices.add(new SlideImage((row - 1), (column)));
                }
            }

            // Need to handle backtracking here

            // This chunk of code randomly chooses a new possible position
            // and switches the values of the units
            // row and column is changed to the new coordinate where the
            // blank space is and then clears out the ArrayList for the
            // next swap
            int number = rand.nextInt(choices.size());
            temp = puzzleArray[choices.get(number).newRow][choices.get(number).newColumn];
            puzzleArray[choices.get(number).newRow][choices.get(number).newColumn] = puzzleArray[row][column];
            puzzleArray[row][column] = temp;
            row = choices.get(number).newRow;
            column = choices.get(number).newColumn;
            choices.clear();

        }
        return puzzleArray;
    }

    public static void main(String[] args) throws IOException {
        SplitImage();
    }
}

