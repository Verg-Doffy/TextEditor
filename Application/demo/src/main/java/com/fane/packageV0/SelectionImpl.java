package com.fane.packageV0;

/**
 * Provides access to selection control operations
 *
 * @author Mohamed AL AFTAN Djakaridja FANE
 * @version 1.0
 */

public class SelectionImpl implements Selection {

    private int beginIndex = 0;
    private int endIndex = 0;
    private int bufferBeginIndex = 0;
    private StringBuilder buffer;

    // Constructor
    public SelectionImpl(StringBuilder buffer) {
        this.buffer = buffer;
    }

    
    /**
     * Provides the index of the first character designated
     * by the selection.
     *
     * @return the start index
     */
    public int getBeginIndex() {
        return this.beginIndex;
    }

    /**
     * Provides the index of the first character
     * after the last character designated
     * by the selection.
     *
     * @return the end index
     */
    @Override
    public int getEndIndex() {
        return this.endIndex;
    }

    /**
     * Provides the index of the first character in the buffer
     *
     * @return the buffer's begin index
     */
    @Override
    public int getBufferBeginIndex() {
        return this.bufferBeginIndex;
    }

    /**
     * Provides the index of the first "virtual" character
     * after the end of the buffer
     *
     * @return the post end buffer index
     */
    @Override
    public int getBufferEndIndex() {
        return buffer.length();
    }

    /**
     * Changes the value of the begin index of the selection
     *
     * @param beginIndex, must be within the buffer index range
     * @throws IndexOutOfBoundsException if the beginIndex is out of bounds
     */
    @Override
    public void setBeginIndex(int beginIndex) {
        if (beginIndex < bufferBeginIndex || beginIndex > endIndex)
            throw new IndexOutOfBoundsException(
                    "Begin index can't be negative value and also can't be larger than the end index.");
        else
            this.beginIndex = beginIndex;

    }

    /**
     * Changes the value of the end index of the selection
     *
     * @param endIndex, must be within the buffer index range
     * @throws IndexOutOfBoundsException if the beginIndex is out of bounds
     */
    @Override
    public void setEndIndex(int endIndex) {
        if (endIndex < bufferBeginIndex || beginIndex > endIndex) {
            throw new IndexOutOfBoundsException(
                    "End index can't be negative value and also can't be smaller than the begin index.");
        } else if (endIndex > buffer.length()) {
            throw new IndexOutOfBoundsException("End index can't be larger than the buffer length");
        } else
            this.endIndex = endIndex;
    }

}