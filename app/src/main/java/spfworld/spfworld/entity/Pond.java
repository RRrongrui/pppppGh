package spfworld.spfworld.entity;

import java.util.List;

/**
 * Created by guozhengke on 2016/9/7.
 */
public class Pond {

    /**
     * status : 200
     * message : 获取数据成功
     * data : [{"pon_id":"10","theme":"sdjffj","price":"0.00","lng":"115.906716","lat":"28.665549","content":"m.yundiaoke.cn","status":"3","charge":"0","is_del":"0","juli":606.84},{"pon_id":"9","theme":"说得对","price":"10.00","lng":"115.906716","lat":"28.665549","content":"m.yundiaoke.cn","status":"0","charge":"0","is_del":"0","juli":606.84},{"pon_id":"8","theme":"1111111","price":"22.00","lng":"115.906716","lat":"28.665549","content":"m.yundiaoke.cn","status":"4","charge":"1","is_del":"0","juli":606.84},{"pon_id":"7","theme":"的说法是电风扇","price":"1.00","lng":"121.232026","lat":"31.111998","content":"m.yundiaoke.cn","status":"0","charge":"0","is_del":"0","juli":0},{"pon_id":"6","theme":"史蒂芬孙","price":"1.00","lng":"121.231318","lat":"31.111480","content":"m.yundiaoke.cn","status":"0","charge":"0","is_del":"0","juli":0.08},{"pon_id":"3","theme":"你个SB啊啊啊啊","price":"12.00","lng":"115.906716","lat":"28.665549","content":"m.yundiaoke.cn/Uploads/Ueditor/image/20160902/57c948be946b4.jpg","status":"5","charge":"1","is_del":"0","juli":606.84},{"pon_id":"2","theme":"大唐","price":"100.00","lng":"121.306374","lat":"31.200092","content":"m.yundiaoke.cn/Uploads/Ueditor/image/20160902/57c93def18e89.jpg","status":"2","charge":"0","is_del":"0","juli":9.72},{"pon_id":"1","theme":"雷公湖gfff","price":"100.00","lng":"121.395334","lat":"31.160489","content":"m.yundiaoke.cn","status":"5","charge":"0","is_del":"0","juli":18.39}]
     */

    private int status;
    private String message;
    /**
     * pon_id : 10
     * theme : sdjffj
     * price : 0.00
     * lng : 115.906716
     * lat : 28.665549
     * content : m.yundiaoke.cn
     * status : 3
     * charge : 0
     * is_del : 0
     * juli : 606.84
     */

    private List<DataBean> data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        private String pon_id;
        private String theme;
        private String price;
        private String lng;
        private String lat;
        private String content;
        private String status;
        private String charge;
        private String is_del;
        private double juli;

        public String getPon_id() {
            return pon_id;
        }

        public void setPon_id(String pon_id) {
            this.pon_id = pon_id;
        }

        public String getTheme() {
            return theme;
        }

        public void setTheme(String theme) {
            this.theme = theme;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getLng() {
            return lng;
        }

        public void setLng(String lng) {
            this.lng = lng;
        }

        public String getLat() {
            return lat;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getCharge() {
            return charge;
        }

        public void setCharge(String charge) {
            this.charge = charge;
        }

        public String getIs_del() {
            return is_del;
        }

        public void setIs_del(String is_del) {
            this.is_del = is_del;
        }

        public double getJuli() {
            return juli;
        }

        public void setJuli(double juli) {
            this.juli = juli;
        }
    }
}
