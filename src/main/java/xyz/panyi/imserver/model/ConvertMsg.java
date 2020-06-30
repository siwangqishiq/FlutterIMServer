package xyz.panyi.imserver.model;

/**
 *  自动编码接口
 */
public interface ConvertMsg {

//    /**
//     *
//     * @param byteData
//     * @return
//     */
//    T convertObj(byte[] byteData);

    /**
     * data to bytes
     * @return
     */
    byte[] toBytes();

    /**
     *  msg type
     * @return
     */
    int code();
}//end class
