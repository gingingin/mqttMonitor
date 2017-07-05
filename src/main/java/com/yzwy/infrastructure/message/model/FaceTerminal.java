package com.yzwy.infrastructure.message.model;

/**
 * Created by gin on 2017/6/6.
 */
public class FaceTerminal extends BaseParam {
    /**
     * 访客图片地址--发送给业主端用 80bytes长度 字符串序列 不足补0 下同
     */
    private String photoUrl;
    /**
     * 上传人脸图库 成员id 长度21bytes
     */
    private String memberId;
    /**
     * 上传人脸图库 成员id 长度4bytes
     */
    private String memberId1;
    /**
     * 上传人脸图库 图片url 长度80bytes 字符串序列 不足补0 下同
     */
    private String photoUrl1;
    /**
     * 上传人脸图库 成员id长度4bytes
     */
    private String memberId2;
    /**
     * 上传人脸图库 图片url 长度80bytes
     */
    private String photoUrl2;
    /**
     * 上传人脸图库 成员id长度4bytes
     */
    private String memberId3;
    /**
     * 上传人脸图库 图片url 长度80bytes
     */
    private String photoUrl3;
    /**
     * 开门命令 1bytes
     */
    private String doorCommand;


    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getMemberId1() {
        return memberId1;
    }

    public void setMemberId1(String memberId1) {
        this.memberId1 = memberId1;
    }

    public String getPhotoUrl1() {
        return photoUrl1;
    }

    public void setPhotoUrl1(String photoUrl1) {
        this.photoUrl1 = photoUrl1;
    }

    public String getMemberId2() {
        return memberId2;
    }

    public void setMemberId2(String memberId2) {
        this.memberId2 = memberId2;
    }

    public String getPhotoUrl2() {
        return photoUrl2;
    }

    public void setPhotoUrl2(String photoUrl2) {
        this.photoUrl2 = photoUrl2;
    }

    public String getMemberId3() {
        return memberId3;
    }

    public void setMemberId3(String memberId3) {
        this.memberId3 = memberId3;
    }

    public String getPhotoUrl3() {
        return photoUrl3;
    }

    public void setPhotoUrl3(String photoUrl3) {
        this.photoUrl3 = photoUrl3;
    }

    public String getDoorCommand() {
        return doorCommand;
    }

    public void setDoorCommand(String doorCommand) {
        this.doorCommand = doorCommand;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }
}
