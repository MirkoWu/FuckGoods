package com.mirkowu.fuckgoods.network.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Administrator on 2016/12/30 0030.
 */

public class GankBean implements Parcelable {
   /* "_id": "5865ada0421aa94dc1ac0ad2",
            "createdAt": "2016-12-30T08:43:12.76Z",
            "desc": "\u7c7b\u4f3c\u624b\u673aQQ\u754c\u9762\u53f3\u4e0a\u89d2\u7684\u5f39\u51fa\u83dc\u5355\uff0c\u4f7f\u7528 recyclerview \u548c popupwindow \u5c01\u88c5\u4e86\u4e00\u4e0b\u3002",
            "images": [
            "http://img.gank.io/38a996f8-bbba-4418-bc0e-6eb877a320c5"
            ],
            "publishedAt": "2016-12-30T16:16:11.125Z",
            "source": "chrome",
            "type": "Android",
            "url": "https://github.com/zaaach/TopRightMenu",
            "used": true,
            "who": "github"
}, */
    public String _id;//
    public String createdAt;//
    public String desc;//
    public List<String> images;
    public String publishedAt;//
    public String source;//
    public String type;//
    public String url;//
    public boolean used;//
    public String who;//

    public GankBean() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this._id);
        dest.writeString(this.createdAt);
        dest.writeString(this.desc);
        dest.writeStringList(this.images);
        dest.writeString(this.publishedAt);
        dest.writeString(this.source);
        dest.writeString(this.type);
        dest.writeString(this.url);
        dest.writeByte(this.used ? (byte) 1 : (byte) 0);
        dest.writeString(this.who);
    }

    protected GankBean(Parcel in) {
        this._id = in.readString();
        this.createdAt = in.readString();
        this.desc = in.readString();
        this.images = in.createStringArrayList();
        this.publishedAt = in.readString();
        this.source = in.readString();
        this.type = in.readString();
        this.url = in.readString();
        this.used = in.readByte() != 0;
        this.who = in.readString();
    }

    public static final Creator<GankBean> CREATOR = new Creator<GankBean>() {
        @Override
        public GankBean createFromParcel(Parcel source) {
            return new GankBean(source);
        }

        @Override
        public GankBean[] newArray(int size) {
            return new GankBean[size];
        }
    };
}
