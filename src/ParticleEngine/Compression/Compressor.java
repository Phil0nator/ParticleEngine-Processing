package ParticleEngine.Compression;

import processing.core.PVector;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.Base64;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.InflaterInputStream;

/**
 * A collection of methods for compression and saving data
 */
public final class Compressor {

    /**
     * Turn a PVector into a small string
     * @param p vector
     * @return small string
     */
    public static String compress(PVector p){
        String out = "";
        out+=convertBase64((int)p.x)+",";
        out+=convertBase64((int)p.y)+",";
        out+=convertBase64((int)p.z);
        return out;
    }

    /**
     * Turn a small PVector string into a PVector
     * @param s string
     * @return PVector
     */
    public static PVector decompress(String s){
        PVector out = new PVector();
        String[] coords = s.split(",");
        out.x = (int)Long.valueOf(convertBase64(coords[0]), 16).longValue();
        out.y = (int)Long.valueOf(convertBase64(coords[1]), 16).longValue();
        out.z = (int)Long.valueOf(convertBase64(coords[2]), 16).longValue();
        return out;
    }

    private static void copyFileInto(File a, File b){

        try{
            FileInputStream ain = new FileInputStream(a);
            FileOutputStream bout = new FileOutputStream(b);
            int data;
            while ((data=ain.read())!=-1)
            {
                bout.write(data);
            }
        }catch(Exception e){
            e.printStackTrace();
        }


    }

    /**
     * Compress the contents of a file and overwrite.
     * @param path file
     */
    public static void compressFile(String path){
        try {
            //Assign the original file : file to
            //FileInputStream for reading data
            FileInputStream fis= new FileInputStream(path);

            //Assign compressed file:file2 to FileOutputStream
            File tmp = File.createTempFile("pepcache",".tmp");
            tmp.deleteOnExit();
            FileOutputStream fos=new FileOutputStream(tmp);

            //Assign FileOutputStream to DeflaterOutputStream
            DeflaterOutputStream dos=new DeflaterOutputStream(fos);

            //read data from FileInputStream and write it into DeflaterOutputStream
            int data;
            while ((data=fis.read())!=-1)
            {
                dos.write(data);
            }
            //close the file
            fis.close();
            dos.close();
            copyFileInto(tmp, new File(path));
            tmp.delete();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Decompress contents of a file and overwrite.
     * @param path file
     */
    public static void decompressFile(String path){

        try {

            //assign Input File : file2 to FileInputStream for reading data
            FileInputStream fis = new FileInputStream(path);

            //assign output file: file3 to FileOutputStream for reading the data
            File tmp = File.createTempFile("pepcache",".tmp");
            tmp.deleteOnExit();
            FileOutputStream fos = new FileOutputStream(tmp);

            //assign inflaterInputStream to FileInputStream for uncompressing the data
            InflaterInputStream iis = new InflaterInputStream(fis);

            //read data from inflaterInputStream and write it into FileOutputStream
            int data;
            while ((data = iis.read()) != -1) {
                fos.write(data);
            }

            //close the files
            fos.close();
            iis.close();
            copyFileInto(tmp,new File(path));
            tmp.delete();
        }catch(Exception e){
            e.printStackTrace();
        }



    }


    private static String convertBase64(int val){
        Base64.Encoder e = Base64.getEncoder();
        return e.encodeToString(Integer.toHexString(val).getBytes());
    }

    private static String convertBase64(String hex){
        Base64.Decoder d = Base64.getDecoder();
        return new String(d.decode(hex));
    }





}
