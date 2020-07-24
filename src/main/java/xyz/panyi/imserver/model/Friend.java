package xyz.panyi.imserver.model;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class Friend extends Codec {
    private long uid;
    private int sex;
    private String avator;
    private String account;
    private String nick;
    private int age;
    private String desc;


    /**

     *
     * @param user
     * @return
     */
    public static Friend buildFriendFromUser(User user){
        Friend result = new Friend();
        if(user == null)
            return result;

        result.setUid(user.getUid());
        result.setAccount(user.getAccount());
        result.setAvator(user.getAvator());
        result.setSex(user.getSex());
        result.setNick(user.getDisplayName());
        result.setAge(user.getAge());
        result.setDesc(user.getDesc());

        return result;
    }



    @Override
    public void decode(ByteBuf byteBuf) {

        uid = readLong(byteBuf);
        sex = readInt(byteBuf);
        avator = readString(byteBuf);
        account = readString(byteBuf);
        nick = readString(byteBuf);
        age = readInt(byteBuf);
        desc = readString(byteBuf);
    }

    @Override
    public ByteBuf encode() {
        ByteBuf byteBuf = Unpooled.buffer(512);

        writeLong(byteBuf , uid);
        writeInt(byteBuf , sex);
        writeString(byteBuf , avator);
        writeString(byteBuf , account);
        writeString(byteBuf, nick);
        writeInt(byteBuf , age);
        writeString(byteBuf, desc);

        return byteBuf;
    }

    @Override
    public int code() {
        return 0;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getAvator() {
        return avator;
    }

    public void setAvator(String avator) {
        this.avator = avator;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "Friend{" +
                "uid=" + uid +
                ", sex=" + sex +
                ", avator='" + avator + '\'' +
                ", account='" + account + '\'' +
                ", nick='" + nick + '\'' +
                '}';
    }
}
