package com.fane;

public class SelectionImpl implements Selection {

    private int beginIndex = 0;
    private int endIndex = 0;
    private int bufferBeginIndex = 0;
    private StringBuilder buffer;

    // Constructor
    public SelectionImpl(StringBuilder buffer) {
        this.buffer = buffer;
    }

    public int getBeginIndex() {
        return this.beginIndex;
    }

    @Override
    public int getEndIndex() {
        return this.endIndex;
    }

    @Override
    public int getBufferBeginIndex() {
        return this.bufferBeginIndex;
    }

    @Override
    public int getBufferEndIndex() {
        return buffer.length();
    }

    @Override
    public void setBeginIndex(int beginIndex) {
        if (beginIndex < bufferBeginIndex || beginIndex > endIndex)
            throw new IndexOutOfBoundsException(
                    "Begin index can't be negative value and also can't be larger than the end index.");
        else
            this.beginIndex = beginIndex;

    }

    @Override
    public void setEndIndex(int endIndex) {
        if (endIndex < bufferBeginIndex || beginIndex > endIndex || endIndex > buffer.length())
            throw new IndexOutOfBoundsException(
                    "End index can't be negative value, can't be larger than the buffer's length and also can't be smaller than the begin index.");
        else
            this.endIndex = endIndex;
    }

}
