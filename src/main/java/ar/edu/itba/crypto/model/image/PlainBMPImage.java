package ar.edu.itba.crypto.model.image;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import static java.lang.System.exit;

public class PlainBMPImage {

    public byte[] imageData;
    private int offset;
    private int width;
    private int heigth ;
    private int bpp;
    private int bytesPerComponent;
    private int size;

    public PlainBMPImage(byte[] bytes) {
        imageData = bytes;
        size = bytes.length;
        validate();
    }

    private void validate() {
        ByteBuffer bb = ByteBuffer.wrap(imageData).order(ByteOrder.LITTLE_ENDIAN);
        //Signature
        bb.getChar();
        //Size
        bb.getInt();
        //zeros
        bb.getChar();
        bb.getChar();
        //pixels offset in bytes
        offset = bb.getInt();
        //header size
        if(bb.getInt() != 40) {
            System.err.println("This bmp file is not V3");
            exit(-1);
        }
        //width in pixels
        width = bb.getInt();
        //height in pixels
        heigth = bb.getInt();
        //planes
        bb.getChar();
        //BitsPerPixel
        bpp = bb.getChar();
        if(bpp%24 != 0) {
            System.err.println("This bmp file is not RGB compliant");
            exit(-1);
        }
        bytesPerComponent = bpp/24;
        //compression
        bb.getInt();
        //Other stuff we don't care about
    }

    public int indexOfComponent(int i){
        return offset + i * bytesPerComponent;
    }

    public byte component(int i){
        return imageData[indexOfComponent(i)];
    }

    public int componentsSize() {
        return (size - offset)/bytesPerComponent;
    }

    public int getOffset() {
        return offset;
    }

    public int getWidth() {
        return width;
    }

    public int getHeigth() {
        return heigth;
    }

    public int getBpp() {
        return bpp;
    }

    public int getBitsPerComponent() {
        return bytesPerComponent*8;
    }
}
