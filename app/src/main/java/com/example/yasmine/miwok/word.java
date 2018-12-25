package com.example.yasmine.miwok;

public class word {

    private String mDefaultTranslation;
    private String mMiwokeTranslation;
    private int mImageResourceId = NO_IMAGE_PROVIDED;
    private static final int NO_IMAGE_PROVIDED = -1;
    private int mAudioResourceId;
    private int mIconResourceId;


    public word(String DefaultTranslation , String MiwokeTranslation,int AudioResourceId , int IconResourceId){

        mDefaultTranslation = DefaultTranslation;
        mMiwokeTranslation =  MiwokeTranslation;
        mAudioResourceId = AudioResourceId;
        mIconResourceId = IconResourceId;


    }
    public word(String DefaultTranslation , String MiwokeTranslation,int ImageResourceId ,int AudioResourceId,
                int IconResourceId){

        mDefaultTranslation = DefaultTranslation;
        mMiwokeTranslation =  MiwokeTranslation;
        mImageResourceId = ImageResourceId;
        mAudioResourceId = AudioResourceId;
        mIconResourceId = IconResourceId;

    }

    // Get default translation of word
    public String getDefaultTranslation(){

        return mDefaultTranslation;
    }


    // Get miwok translation of word
    public String getMiwokeTranslation() {

        return mMiwokeTranslation;
    }

    //Get image resource ID
    public int getImageResourceId(){

        return mImageResourceId;
    }

    public int getIconResourceId(){

        return mIconResourceId;
    }

    //return true if id not equal to -1
    public boolean hasImage(){

        return mImageResourceId != NO_IMAGE_PROVIDED ;
    }

    //Get MediaPlayer ID
    public int getAudioResourceId(){

        return mAudioResourceId;
    }
}
