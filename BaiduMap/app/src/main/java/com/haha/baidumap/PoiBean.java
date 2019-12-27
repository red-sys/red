package com.haha.baidumap;

import java.util.List;

/**
 * Created by Zs on 2019/4/4.
 */

public class PoiBean {

    /**
     * showapi_res_code : 0
     * showapi_res_error :
     * showapi_res_body : {"pagebean":{"allNum":32,"allPages":2,"contentlist":[{"address":"颜山公园路4号原山国家森林公园","areaId":"3940","areaName":"临淄区","cityId":"299","cityName":"淄博","id":"7472","location":{"lat":"36.49018821","lon":"117.84976357"},"name":"原山泰山行宫","picList":[{"picUrl":"http://pic3.40017.cn/scenery/destination/2015/04/16/09/33K4AW.jpg","picUrlSmall":"http://pic3.40017.cn/scenery/destination/2015/04/16/09/33K4AW_130x130_00.jpg"},{"picUrl":"http://pic3.40017.cn/scenery/destination/2015/04/16/09/slKVTv.jpg","picUrlSmall":"http://pic3.40017.cn/scenery/destination/2015/04/16/09/slKVTv_130x130_00.jpg"}],"priceList":[],"proId":"22","proName":"山东","summary":"原山泰山行宫位于博山城区西南的凤凰山巅，为市级重点文物保护单位。行宫始建于1602年（明万历三十年），历经几度劫难，清代、民国曾多次重修。现存建筑和布局均系明、清风格。现为颜山公园名胜之一。"}],"currentPage":1,"maxResult":20},"ret_code":0}
     */

    private int showapi_res_code;
    private String showapi_res_error;
    private ShowapiResBodyBean showapi_res_body;

    public int getShowapi_res_code() {
        return showapi_res_code;
    }

    public void setShowapi_res_code(int showapi_res_code) {
        this.showapi_res_code = showapi_res_code;
    }

    public String getShowapi_res_error() {
        return showapi_res_error;
    }

    public void setShowapi_res_error(String showapi_res_error) {
        this.showapi_res_error = showapi_res_error;
    }

    public ShowapiResBodyBean getShowapi_res_body() {
        return showapi_res_body;
    }

    public void setShowapi_res_body(ShowapiResBodyBean showapi_res_body) {
        this.showapi_res_body = showapi_res_body;
    }

    public static class ShowapiResBodyBean {
        /**
         * pagebean : {"allNum":32,"allPages":2,"contentlist":[{"address":"颜山公园路4号原山国家森林公园","areaId":"3940","areaName":"临淄区","cityId":"299","cityName":"淄博","id":"7472","location":{"lat":"36.49018821","lon":"117.84976357"},"name":"原山泰山行宫","picList":[{"picUrl":"http://pic3.40017.cn/scenery/destination/2015/04/16/09/33K4AW.jpg","picUrlSmall":"http://pic3.40017.cn/scenery/destination/2015/04/16/09/33K4AW_130x130_00.jpg"},{"picUrl":"http://pic3.40017.cn/scenery/destination/2015/04/16/09/slKVTv.jpg","picUrlSmall":"http://pic3.40017.cn/scenery/destination/2015/04/16/09/slKVTv_130x130_00.jpg"}],"priceList":[],"proId":"22","proName":"山东","summary":"原山泰山行宫位于博山城区西南的凤凰山巅，为市级重点文物保护单位。行宫始建于1602年（明万历三十年），历经几度劫难，清代、民国曾多次重修。现存建筑和布局均系明、清风格。现为颜山公园名胜之一。"}],"currentPage":1,"maxResult":20}
         * ret_code : 0
         */

        private PagebeanBean pagebean;
        private int ret_code;

        public PagebeanBean getPagebean() {
            return pagebean;
        }

        public void setPagebean(PagebeanBean pagebean) {
            this.pagebean = pagebean;
        }

        public int getRet_code() {
            return ret_code;
        }

        public void setRet_code(int ret_code) {
            this.ret_code = ret_code;
        }

        public static class PagebeanBean {
            /**
             * allNum : 32
             * allPages : 2
             * contentlist : [{"address":"颜山公园路4号原山国家森林公园","areaId":"3940","areaName":"临淄区","cityId":"299","cityName":"淄博","id":"7472","location":{"lat":"36.49018821","lon":"117.84976357"},"name":"原山泰山行宫","picList":[{"picUrl":"http://pic3.40017.cn/scenery/destination/2015/04/16/09/33K4AW.jpg","picUrlSmall":"http://pic3.40017.cn/scenery/destination/2015/04/16/09/33K4AW_130x130_00.jpg"},{"picUrl":"http://pic3.40017.cn/scenery/destination/2015/04/16/09/slKVTv.jpg","picUrlSmall":"http://pic3.40017.cn/scenery/destination/2015/04/16/09/slKVTv_130x130_00.jpg"}],"priceList":[],"proId":"22","proName":"山东","summary":"原山泰山行宫位于博山城区西南的凤凰山巅，为市级重点文物保护单位。行宫始建于1602年（明万历三十年），历经几度劫难，清代、民国曾多次重修。现存建筑和布局均系明、清风格。现为颜山公园名胜之一。"}]
             * currentPage : 1
             * maxResult : 20
             */

            private int allNum;
            private int allPages;
            private int currentPage;
            private int maxResult;
            private List<ContentlistBean> contentlist;

            public int getAllNum() {
                return allNum;
            }

            public void setAllNum(int allNum) {
                this.allNum = allNum;
            }

            public int getAllPages() {
                return allPages;
            }

            public void setAllPages(int allPages) {
                this.allPages = allPages;
            }

            public int getCurrentPage() {
                return currentPage;
            }

            public void setCurrentPage(int currentPage) {
                this.currentPage = currentPage;
            }

            public int getMaxResult() {
                return maxResult;
            }

            public void setMaxResult(int maxResult) {
                this.maxResult = maxResult;
            }

            public List<ContentlistBean> getContentlist() {
                return contentlist;
            }

            public void setContentlist(List<ContentlistBean> contentlist) {
                this.contentlist = contentlist;
            }

            public static class ContentlistBean {
                /**
                 * address : 颜山公园路4号原山国家森林公园
                 * areaId : 3940
                 * areaName : 临淄区
                 * cityId : 299
                 * cityName : 淄博
                 * id : 7472
                 * location : {"lat":"36.49018821","lon":"117.84976357"}
                 * name : 原山泰山行宫
                 * picList : [{"picUrl":"http://pic3.40017.cn/scenery/destination/2015/04/16/09/33K4AW.jpg","picUrlSmall":"http://pic3.40017.cn/scenery/destination/2015/04/16/09/33K4AW_130x130_00.jpg"},{"picUrl":"http://pic3.40017.cn/scenery/destination/2015/04/16/09/slKVTv.jpg","picUrlSmall":"http://pic3.40017.cn/scenery/destination/2015/04/16/09/slKVTv_130x130_00.jpg"}]
                 * priceList : []
                 * proId : 22
                 * proName : 山东
                 * summary : 原山泰山行宫位于博山城区西南的凤凰山巅，为市级重点文物保护单位。行宫始建于1602年（明万历三十年），历经几度劫难，清代、民国曾多次重修。现存建筑和布局均系明、清风格。现为颜山公园名胜之一。
                 */

                private String address;
                private String areaId;
                private String areaName;
                private String cityId;
                private String cityName;
                private String id;
                private LocationBean location;
                private String name;
                private String proId;
                private String proName;
                private String summary;
                private List<PicListBean> picList;
                private List<?> priceList;

                public String getAddress() {
                    return address;
                }

                public void setAddress(String address) {
                    this.address = address;
                }

                public String getAreaId() {
                    return areaId;
                }

                public void setAreaId(String areaId) {
                    this.areaId = areaId;
                }

                public String getAreaName() {
                    return areaName;
                }

                public void setAreaName(String areaName) {
                    this.areaName = areaName;
                }

                public String getCityId() {
                    return cityId;
                }

                public void setCityId(String cityId) {
                    this.cityId = cityId;
                }

                public String getCityName() {
                    return cityName;
                }

                public void setCityName(String cityName) {
                    this.cityName = cityName;
                }

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public LocationBean getLocation() {
                    return location;
                }

                public void setLocation(LocationBean location) {
                    this.location = location;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getProId() {
                    return proId;
                }

                public void setProId(String proId) {
                    this.proId = proId;
                }

                public String getProName() {
                    return proName;
                }

                public void setProName(String proName) {
                    this.proName = proName;
                }

                public String getSummary() {
                    return summary;
                }

                public void setSummary(String summary) {
                    this.summary = summary;
                }

                public List<PicListBean> getPicList() {
                    return picList;
                }

                public void setPicList(List<PicListBean> picList) {
                    this.picList = picList;
                }

                public List<?> getPriceList() {
                    return priceList;
                }

                public void setPriceList(List<?> priceList) {
                    this.priceList = priceList;
                }

                public static class LocationBean {
                    /**
                     * lat : 36.49018821
                     * lon : 117.84976357
                     */

                    private String lat;
                    private String lon;

                    public String getLat() {
                        return lat;
                    }

                    public void setLat(String lat) {
                        this.lat = lat;
                    }

                    public String getLon() {
                        return lon;
                    }

                    public void setLon(String lon) {
                        this.lon = lon;
                    }
                }

                public static class PicListBean {
                    /**
                     * picUrl : http://pic3.40017.cn/scenery/destination/2015/04/16/09/33K4AW.jpg
                     * picUrlSmall : http://pic3.40017.cn/scenery/destination/2015/04/16/09/33K4AW_130x130_00.jpg
                     */

                    private String picUrl;
                    private String picUrlSmall;

                    public String getPicUrl() {
                        return picUrl;
                    }

                    public void setPicUrl(String picUrl) {
                        this.picUrl = picUrl;
                    }

                    public String getPicUrlSmall() {
                        return picUrlSmall;
                    }

                    public void setPicUrlSmall(String picUrlSmall) {
                        this.picUrlSmall = picUrlSmall;
                    }
                }
            }
        }
    }
}
