package com.example.nusaht.models;

import com.google.gson.annotations.SerializedName;

public class AudioModel {
    @SerializedName("audios__annotation")
    String audios__annotation;

    @SerializedName("audios__audio")
    String audios__audio;

    @SerializedName("audios__avatar")
    String audios__avatar;

    @SerializedName("audios__language__name")
    String audios__language__name;

    @SerializedName("audios__name")
    String audios__name;


    @SerializedName("audios__streamed")
    String audios__streamed;


    public String getAudios__annotation() {
        return audios__annotation;
    }

    public void setAudios__annotation(String audios__annotation) {
        this.audios__annotation = audios__annotation;
    }

    public String getAudios__audio() {
        return audios__audio;
    }

    public void setAudios__audio(String audios__audio) {
        this.audios__audio = audios__audio;
    }

    public String getAudios__avatar() {
        return audios__avatar;
    }

    public void setAudios__avatar(String audios__avatar) {
        this.audios__avatar = audios__avatar;
    }

    public String getAudios__language__name() {
        return audios__language__name;
    }

    public void setAudios__language__name(String audios__language__name) {
        this.audios__language__name = audios__language__name;
    }

    public String getAudios__name() {
        return audios__name;
    }

    public void setAudios__name(String audios__name) {
        this.audios__name = audios__name;
    }

    public String getAudios__streamed() {
        return audios__streamed;
    }

    public void setAudios__streamed(String audios__streamed) {
        this.audios__streamed = audios__streamed;
    }
}
