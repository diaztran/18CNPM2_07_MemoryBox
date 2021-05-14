package com.example.memorybox;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.media.ExifInterface;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class ShowInforPhotos {
    public static ArrayList<String> listOfImageVideos(Context context, String pathPhotos)
    {
        ArrayList<String> lstInfor=new ArrayList<>();
        //Get file
        File file = new File(pathPhotos);
        String[] parseLink=pathPhotos.split("/");
        String tParseLink=parseLink[parseLink.length-1];
        String[] tailParseLink=tParseLink.split("\\.");
        String nameTail=tailParseLink[tailParseLink.length-1];

        if(!nameTail.equals("mp4")) //Là ảnh Xong
        {
            String nameImage=parseLink[parseLink.length-1]; //Xong
            String pathImage=pathPhotos;//Xong
            String dateTakenImage=new Date(file.lastModified())+""; //Xong
            String sizeImage=handleSize(file.length()); //Xong
            ArrayList<Integer> dimension=handleDimension(pathPhotos,"1"); //Xong
            String dimesionImage= dimension.get(0)+" X "+dimension.get(1); //Xong
            ArrayList<String> exifInfor=handleExif(context,pathPhotos);
            lstInfor.add(nameImage);
            lstInfor.add(pathImage);
            lstInfor.add(dateTakenImage);
            lstInfor.add(sizeImage);
            lstInfor.add(dimesionImage);
            for(String ex:exifInfor)
            {
                lstInfor.add(ex);
            }
        }
        else // Là video
        {
            String nameVideo=parseLink[parseLink.length-1];
            String pathVideo=pathPhotos;
            String dateTakenVideo=new Date(file.lastModified())+"";
            String sizeVideo=handleSize(file.length());
            ArrayList<Integer> dimension=handleDimension(pathPhotos,"2");
            String dimesionVideo= dimension.get(0)+" X "+dimension.get(1);
            //get duration
            MediaMetadataRetriever retriever= new MediaMetadataRetriever();
            retriever.setDataSource(pathPhotos);
            long duration = Long.parseLong(retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION));
            int hours= (int) (duration/(1000*3600));
            int min= (int) ((duration%(1000*3600))/(1000*60));
            int sec=(int) ((duration%(1000*3600))%(1000*60)/1000);
            String displayDuration=hours+":"+min+":"+sec;
            retriever.release();
            ArrayList<String> exifInfor=handleExif(context,pathPhotos);
            String locationVideo=exifInfor.get(exifInfor.size()-1);

            lstInfor.add(nameVideo);
            lstInfor.add(pathVideo);
            lstInfor.add(dateTakenVideo);
            lstInfor.add(sizeVideo);
            lstInfor.add(dimesionVideo);
            lstInfor.add(displayDuration);
            lstInfor.add(locationVideo);
        }
        return  lstInfor;
    }

    //Size MB
    public static String handleSize(long sizePath)
    {
        long fileSizeInBytes = sizePath;
        double fileSizeInKB = fileSizeInBytes / 1024;
        double fileSizeInMB = fileSizeInKB / 1024;
        String strMB=String.format("%.2f",fileSizeInMB); //get MB
        return strMB+" MB";
    }

    //Dimension
    public static ArrayList<Integer> handleDimension(String pathpath,String optionPhoto)
    {
        ArrayList<Integer> dimen=new ArrayList<>();
        if(optionPhoto.equals("1")) //1 là image
        {
            BitmapFactory.Options options=new BitmapFactory.Options();
            options.inJustDecodeBounds=true;
            BitmapFactory.decodeFile(pathpath, options);
            int imageHeight = options.outHeight;
            int imageWidth = options.outWidth;
            dimen.add(imageWidth);
            dimen.add(imageHeight);
        }
        else //là video
        {
            MediaPlayer mp = new MediaPlayer();
            try {
                mp.setDataSource(pathpath);
                mp.prepare();
                int widthVideo = mp.getVideoWidth();
                int heightVideo = mp.getVideoHeight();
                dimen.add(widthVideo);
                dimen.add(heightVideo);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return dimen;
    }

    //check null
    public static String checkNotNull(String name)
    {
        if(name!=null)
        {
            return name;
        }
        else
        {
            return "unknown";
        }
    }

    //ExifInterface Image
    public static ArrayList<String> handleExif(Context context,String pathpath)
    {
        ArrayList<String> exifInfor=new ArrayList<>();
        try {
            ExifInterface exif=new ExifInterface(pathpath);
            String orientation=checkNotNull(exif.getAttribute(ExifInterface.TAG_ORIENTATION)); //get orientation Xong
            String makeDevice=checkNotNull(exif.getAttribute(ExifInterface.TAG_MAKE)); //get makeDevice Xong
            String device=checkNotNull(exif.getAttribute(ExifInterface.TAG_MODEL)); //get device taking
            String apertureValue=checkNotNull(exif.getAttribute(ExifInterface.TAG_APERTURE_VALUE)); //get aperture value
            String forcalLenght=checkNotNull(exif.getAttribute(ExifInterface.TAG_FOCAL_LENGTH)); //get Focal Lenght
            String isoValue=checkNotNull(exif.getAttribute(ExifInterface.TAG_ISO_SPEED_RATINGS)); //get iso
            String shutterSpeed=checkNotNull(exif.getAttribute(ExifInterface.TAG_SHUTTER_SPEED_VALUE)); //get shulter speed
            String whiteBalance=checkNotNull(exif.getAttribute(ExifInterface.TAG_WHITE_BALANCE)); //get white Balance

//            //get Location
            String addressLocation="unknown";
            if(!pathpath.endsWith(".mp4"))
            {

                float[] latLong=new float[2];
                boolean hasLatLong = exif.getLatLong(latLong);

                if (hasLatLong) {
                    Geocoder geocoder;
                    List<Address> addresses;
                    geocoder=new Geocoder(context, Locale.getDefault());
                    addresses=geocoder.getFromLocation(latLong[0],latLong[1],1);
                    addressLocation = addresses.get(0).getAddressLine(0);
                }
            }
            else
            {
                MediaMetadataRetriever mediaMetadataRetriever=new MediaMetadataRetriever();
                mediaMetadataRetriever.setDataSource(pathpath);
                String locationVideo=mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_LOCATION);
                if(locationVideo!=null)
                {
                    locationVideo=locationVideo.replace("+"," ");
                    locationVideo=locationVideo.replace("/","");
                    String[]token=locationVideo.split(" ");
                    Geocoder geocoder;
                    List<Address> addresses;
                    geocoder=new Geocoder(context, Locale.getDefault());
                    addresses=geocoder.getFromLocation(Float.parseFloat(token[1]),Float.parseFloat(token[2]),1);
                    addressLocation = addresses.get(0).getAddressLine(0);
                }

            }


            exifInfor.add(orientation);
            exifInfor.add(makeDevice);
            exifInfor.add(device);
            exifInfor.add(apertureValue);
            exifInfor.add(forcalLenght);
            exifInfor.add(isoValue);
            exifInfor.add(shutterSpeed);
            exifInfor.add(whiteBalance);
            exifInfor.add(addressLocation);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return exifInfor;
    }


    public static String converMonth(String x)
    {
        String temp="";
        if(x.equals("Jan"))
        {
            temp="01";
        }
        else if(x.equals("Feb"))
        {
            temp="02";
        }
        else if(x.equals("Mar"))
        {
            temp="03";
        }
        else if(x.equals("Apr"))
        {
            temp="04";
        }
        else if(x.equals("May"))
        {
            temp="05";
        }
        else if(x.equals("Jun"))
        {
            temp="06";
        }
        else if(x.equals("Jul"))
        {
            temp="07";
        }
        else if(x.equals("Aug"))
        {
            temp="08";
        }
        else if(x.equals("Sep"))
        {
            temp="09";
        }
        else if(x.equals("Oct"))
        {
            temp="10";
        }
        else if(x.equals("Nov"))
        {
            temp="11";
        }
        else if(x.equals("Dec"))
        {
            temp="12";
        }
        return temp;
    }

    public static ArrayList<String> standardDate(String x)
    {
        ArrayList<String> temp=new ArrayList<>();
        String[]tokensDDMMYY=x.split(" ");
        temp.add(tokensDDMMYY[0]);
        temp.add(tokensDDMMYY[2]);
//        temp.add(tokensDDMMYY[1]);
        temp.add(converMonth(tokensDDMMYY[1]));
        temp.add(tokensDDMMYY[tokensDDMMYY.length-1]);
        return temp;
    }
    //Fri Apr 16 22:51:29 GMT+07:00 2021
    public static ArrayList<String> onlyGetDate(List<Photo> listVideo)
    {
        ArrayList<String> tempGetDate=new ArrayList<>();
        for(Photo video:listVideo)
        {
            String pathPhotos=video.getPath();
            File file = new File(pathPhotos);
            String dateTakenImage=new Date(file.lastModified())+"";
            ArrayList<String> dateString=standardDate(dateTakenImage);
//            String tempStringDate=dateString.get(0)+" "+dateString.get(1)+" "+dateString.get(2)+" "+dateString.get(3);
            String tempStringDate=dateString.get(1)+" "+dateString.get(2)+" "+dateString.get(3);
            if(!tempGetDate.contains(tempStringDate))
            {
                tempGetDate.add(tempStringDate);
            }
        }
        return tempGetDate;
    }

    public static String convertPathToDate(Photo x)
    {
        File file = new File(x.getPath());
        String dateTakenImage=new Date(file.lastModified())+"";
        ArrayList<String> dateString=standardDate(dateTakenImage);
//        String tempStringDate=dateString.get(0)+" "+dateString.get(1)+" "+dateString.get(2)+" "+dateString.get(3);
        String tempStringDate=dateString.get(1)+" "+dateString.get(2)+" "+dateString.get(3);
        return tempStringDate;
    }

    public static String convertPathImageToDate(String path)
    {
        File file = new File(path);
        String dateTakenImage=new Date(file.lastModified())+"";
        ArrayList<String> dateString=standardDate(dateTakenImage);
        String tempStringDate=dateString.get(1)+" "+dateString.get(2)+" "+dateString.get(3);
        return tempStringDate;
    }
    public static Map<String,ArrayList<Photo>> groupPhotosFollow(List<Photo> lstVideo, List<String> getOnlyDate)
    {
        Map<String,ArrayList<Photo>> groupHashMap=new HashMap<>();
        for(String tempGetDay:getOnlyDate)
        {
            ArrayList<Photo> lvdtemp=new ArrayList<>();
            for(Photo tempVideo:lstVideo)
            {
                if(convertPathToDate(tempVideo).equals(tempGetDay))
                {
                    lvdtemp.add(tempVideo);
                }
            }
            groupHashMap.put(tempGetDay,lvdtemp);
        }
        return groupHashMap;
    }

    public static List<String> sortDateDes(List<String>getDate)
    {
        Collections.sort(getDate, new Comparator<String>() {
            //            DateFormat f=new SimpleDateFormat("EEE dd MM yyyy");
            DateFormat f=new SimpleDateFormat("dd MM yyyy");
            @Override
            public int compare(String o1, String o2) {
                try {
                    return f.parse(o1).compareTo(f.parse(o2));
                } catch (ParseException e) {
                    throw new IllegalArgumentException(e);
                }
            }
        });
        Collections.reverse(getDate);
        return getDate;
    }
}
