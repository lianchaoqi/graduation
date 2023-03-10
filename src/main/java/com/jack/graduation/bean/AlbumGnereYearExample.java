package com.jack.graduation.bean;

import java.util.ArrayList;
import java.util.List;

public class AlbumGnereYearExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table ads_album_gnere_year_nd
     *
     * @mbggenerated Fri Jan 20 13:10:23 CST 2023
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table ads_album_gnere_year_nd
     *
     * @mbggenerated Fri Jan 20 13:10:23 CST 2023
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table ads_album_gnere_year_nd
     *
     * @mbggenerated Fri Jan 20 13:10:23 CST 2023
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ads_album_gnere_year_nd
     *
     * @mbggenerated Fri Jan 20 13:10:23 CST 2023
     */
    public AlbumGnereYearExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ads_album_gnere_year_nd
     *
     * @mbggenerated Fri Jan 20 13:10:23 CST 2023
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ads_album_gnere_year_nd
     *
     * @mbggenerated Fri Jan 20 13:10:23 CST 2023
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ads_album_gnere_year_nd
     *
     * @mbggenerated Fri Jan 20 13:10:23 CST 2023
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ads_album_gnere_year_nd
     *
     * @mbggenerated Fri Jan 20 13:10:23 CST 2023
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ads_album_gnere_year_nd
     *
     * @mbggenerated Fri Jan 20 13:10:23 CST 2023
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ads_album_gnere_year_nd
     *
     * @mbggenerated Fri Jan 20 13:10:23 CST 2023
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ads_album_gnere_year_nd
     *
     * @mbggenerated Fri Jan 20 13:10:23 CST 2023
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ads_album_gnere_year_nd
     *
     * @mbggenerated Fri Jan 20 13:10:23 CST 2023
     */
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ads_album_gnere_year_nd
     *
     * @mbggenerated Fri Jan 20 13:10:23 CST 2023
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ads_album_gnere_year_nd
     *
     * @mbggenerated Fri Jan 20 13:10:23 CST 2023
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table ads_album_gnere_year_nd
     *
     * @mbggenerated Fri Jan 20 13:10:23 CST 2023
     */
    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andGenreIsNull() {
            addCriterion("genre is null");
            return (Criteria) this;
        }

        public Criteria andGenreIsNotNull() {
            addCriterion("genre is not null");
            return (Criteria) this;
        }

        public Criteria andGenreEqualTo(String value) {
            addCriterion("genre =", value, "genre");
            return (Criteria) this;
        }

        public Criteria andGenreNotEqualTo(String value) {
            addCriterion("genre <>", value, "genre");
            return (Criteria) this;
        }

        public Criteria andGenreGreaterThan(String value) {
            addCriterion("genre >", value, "genre");
            return (Criteria) this;
        }

        public Criteria andGenreGreaterThanOrEqualTo(String value) {
            addCriterion("genre >=", value, "genre");
            return (Criteria) this;
        }

        public Criteria andGenreLessThan(String value) {
            addCriterion("genre <", value, "genre");
            return (Criteria) this;
        }

        public Criteria andGenreLessThanOrEqualTo(String value) {
            addCriterion("genre <=", value, "genre");
            return (Criteria) this;
        }

        public Criteria andGenreLike(String value) {
            addCriterion("genre like", value, "genre");
            return (Criteria) this;
        }

        public Criteria andGenreNotLike(String value) {
            addCriterion("genre not like", value, "genre");
            return (Criteria) this;
        }

        public Criteria andGenreIn(List<String> values) {
            addCriterion("genre in", values, "genre");
            return (Criteria) this;
        }

        public Criteria andGenreNotIn(List<String> values) {
            addCriterion("genre not in", values, "genre");
            return (Criteria) this;
        }

        public Criteria andGenreBetween(String value1, String value2) {
            addCriterion("genre between", value1, value2, "genre");
            return (Criteria) this;
        }

        public Criteria andGenreNotBetween(String value1, String value2) {
            addCriterion("genre not between", value1, value2, "genre");
            return (Criteria) this;
        }

        public Criteria andAlbumYearOfPubIsNull() {
            addCriterion("album_year_of_pub is null");
            return (Criteria) this;
        }

        public Criteria andAlbumYearOfPubIsNotNull() {
            addCriterion("album_year_of_pub is not null");
            return (Criteria) this;
        }

        public Criteria andAlbumYearOfPubEqualTo(String value) {
            addCriterion("album_year_of_pub =", value, "albumYearOfPub");
            return (Criteria) this;
        }

        public Criteria andAlbumYearOfPubNotEqualTo(String value) {
            addCriterion("album_year_of_pub <>", value, "albumYearOfPub");
            return (Criteria) this;
        }

        public Criteria andAlbumYearOfPubGreaterThan(String value) {
            addCriterion("album_year_of_pub >", value, "albumYearOfPub");
            return (Criteria) this;
        }

        public Criteria andAlbumYearOfPubGreaterThanOrEqualTo(String value) {
            addCriterion("album_year_of_pub >=", value, "albumYearOfPub");
            return (Criteria) this;
        }

        public Criteria andAlbumYearOfPubLessThan(String value) {
            addCriterion("album_year_of_pub <", value, "albumYearOfPub");
            return (Criteria) this;
        }

        public Criteria andAlbumYearOfPubLessThanOrEqualTo(String value) {
            addCriterion("album_year_of_pub <=", value, "albumYearOfPub");
            return (Criteria) this;
        }

        public Criteria andAlbumYearOfPubLike(String value) {
            addCriterion("album_year_of_pub like", value, "albumYearOfPub");
            return (Criteria) this;
        }

        public Criteria andAlbumYearOfPubNotLike(String value) {
            addCriterion("album_year_of_pub not like", value, "albumYearOfPub");
            return (Criteria) this;
        }

        public Criteria andAlbumYearOfPubIn(List<String> values) {
            addCriterion("album_year_of_pub in", values, "albumYearOfPub");
            return (Criteria) this;
        }

        public Criteria andAlbumYearOfPubNotIn(List<String> values) {
            addCriterion("album_year_of_pub not in", values, "albumYearOfPub");
            return (Criteria) this;
        }

        public Criteria andAlbumYearOfPubBetween(String value1, String value2) {
            addCriterion("album_year_of_pub between", value1, value2, "albumYearOfPub");
            return (Criteria) this;
        }

        public Criteria andAlbumYearOfPubNotBetween(String value1, String value2) {
            addCriterion("album_year_of_pub not between", value1, value2, "albumYearOfPub");
            return (Criteria) this;
        }

        public Criteria andNumOfTracksIsNull() {
            addCriterion("num_of_tracks is null");
            return (Criteria) this;
        }

        public Criteria andNumOfTracksIsNotNull() {
            addCriterion("num_of_tracks is not null");
            return (Criteria) this;
        }

        public Criteria andNumOfTracksEqualTo(Long value) {
            addCriterion("num_of_tracks =", value, "numOfTracks");
            return (Criteria) this;
        }

        public Criteria andNumOfTracksNotEqualTo(Long value) {
            addCriterion("num_of_tracks <>", value, "numOfTracks");
            return (Criteria) this;
        }

        public Criteria andNumOfTracksGreaterThan(Long value) {
            addCriterion("num_of_tracks >", value, "numOfTracks");
            return (Criteria) this;
        }

        public Criteria andNumOfTracksGreaterThanOrEqualTo(Long value) {
            addCriterion("num_of_tracks >=", value, "numOfTracks");
            return (Criteria) this;
        }

        public Criteria andNumOfTracksLessThan(Long value) {
            addCriterion("num_of_tracks <", value, "numOfTracks");
            return (Criteria) this;
        }

        public Criteria andNumOfTracksLessThanOrEqualTo(Long value) {
            addCriterion("num_of_tracks <=", value, "numOfTracks");
            return (Criteria) this;
        }

        public Criteria andNumOfTracksIn(List<Long> values) {
            addCriterion("num_of_tracks in", values, "numOfTracks");
            return (Criteria) this;
        }

        public Criteria andNumOfTracksNotIn(List<Long> values) {
            addCriterion("num_of_tracks not in", values, "numOfTracks");
            return (Criteria) this;
        }

        public Criteria andNumOfTracksBetween(Long value1, Long value2) {
            addCriterion("num_of_tracks between", value1, value2, "numOfTracks");
            return (Criteria) this;
        }

        public Criteria andNumOfTracksNotBetween(Long value1, Long value2) {
            addCriterion("num_of_tracks not between", value1, value2, "numOfTracks");
            return (Criteria) this;
        }

        public Criteria andNumOfSalesIsNull() {
            addCriterion("num_of_sales is null");
            return (Criteria) this;
        }

        public Criteria andNumOfSalesIsNotNull() {
            addCriterion("num_of_sales is not null");
            return (Criteria) this;
        }

        public Criteria andNumOfSalesEqualTo(Long value) {
            addCriterion("num_of_sales =", value, "numOfSales");
            return (Criteria) this;
        }

        public Criteria andNumOfSalesNotEqualTo(Long value) {
            addCriterion("num_of_sales <>", value, "numOfSales");
            return (Criteria) this;
        }

        public Criteria andNumOfSalesGreaterThan(Long value) {
            addCriterion("num_of_sales >", value, "numOfSales");
            return (Criteria) this;
        }

        public Criteria andNumOfSalesGreaterThanOrEqualTo(Long value) {
            addCriterion("num_of_sales >=", value, "numOfSales");
            return (Criteria) this;
        }

        public Criteria andNumOfSalesLessThan(Long value) {
            addCriterion("num_of_sales <", value, "numOfSales");
            return (Criteria) this;
        }

        public Criteria andNumOfSalesLessThanOrEqualTo(Long value) {
            addCriterion("num_of_sales <=", value, "numOfSales");
            return (Criteria) this;
        }

        public Criteria andNumOfSalesIn(List<Long> values) {
            addCriterion("num_of_sales in", values, "numOfSales");
            return (Criteria) this;
        }

        public Criteria andNumOfSalesNotIn(List<Long> values) {
            addCriterion("num_of_sales not in", values, "numOfSales");
            return (Criteria) this;
        }

        public Criteria andNumOfSalesBetween(Long value1, Long value2) {
            addCriterion("num_of_sales between", value1, value2, "numOfSales");
            return (Criteria) this;
        }

        public Criteria andNumOfSalesNotBetween(Long value1, Long value2) {
            addCriterion("num_of_sales not between", value1, value2, "numOfSales");
            return (Criteria) this;
        }

        public Criteria andDtIsNull() {
            addCriterion("dt is null");
            return (Criteria) this;
        }

        public Criteria andDtIsNotNull() {
            addCriterion("dt is not null");
            return (Criteria) this;
        }

        public Criteria andDtEqualTo(String value) {
            addCriterion("dt =", value, "dt");
            return (Criteria) this;
        }

        public Criteria andDtNotEqualTo(String value) {
            addCriterion("dt <>", value, "dt");
            return (Criteria) this;
        }

        public Criteria andDtGreaterThan(String value) {
            addCriterion("dt >", value, "dt");
            return (Criteria) this;
        }

        public Criteria andDtGreaterThanOrEqualTo(String value) {
            addCriterion("dt >=", value, "dt");
            return (Criteria) this;
        }

        public Criteria andDtLessThan(String value) {
            addCriterion("dt <", value, "dt");
            return (Criteria) this;
        }

        public Criteria andDtLessThanOrEqualTo(String value) {
            addCriterion("dt <=", value, "dt");
            return (Criteria) this;
        }

        public Criteria andDtLike(String value) {
            addCriterion("dt like", value, "dt");
            return (Criteria) this;
        }

        public Criteria andDtNotLike(String value) {
            addCriterion("dt not like", value, "dt");
            return (Criteria) this;
        }

        public Criteria andDtIn(List<String> values) {
            addCriterion("dt in", values, "dt");
            return (Criteria) this;
        }

        public Criteria andDtNotIn(List<String> values) {
            addCriterion("dt not in", values, "dt");
            return (Criteria) this;
        }

        public Criteria andDtBetween(String value1, String value2) {
            addCriterion("dt between", value1, value2, "dt");
            return (Criteria) this;
        }

        public Criteria andDtNotBetween(String value1, String value2) {
            addCriterion("dt not between", value1, value2, "dt");
            return (Criteria) this;
        }

        public Criteria andFilenameIsNull() {
            addCriterion("filename is null");
            return (Criteria) this;
        }

        public Criteria andFilenameIsNotNull() {
            addCriterion("filename is not null");
            return (Criteria) this;
        }

        public Criteria andFilenameEqualTo(String value) {
            addCriterion("filename =", value, "filename");
            return (Criteria) this;
        }

        public Criteria andFilenameNotEqualTo(String value) {
            addCriterion("filename <>", value, "filename");
            return (Criteria) this;
        }

        public Criteria andFilenameGreaterThan(String value) {
            addCriterion("filename >", value, "filename");
            return (Criteria) this;
        }

        public Criteria andFilenameGreaterThanOrEqualTo(String value) {
            addCriterion("filename >=", value, "filename");
            return (Criteria) this;
        }

        public Criteria andFilenameLessThan(String value) {
            addCriterion("filename <", value, "filename");
            return (Criteria) this;
        }

        public Criteria andFilenameLessThanOrEqualTo(String value) {
            addCriterion("filename <=", value, "filename");
            return (Criteria) this;
        }

        public Criteria andFilenameLike(String value) {
            addCriterion("filename like", value, "filename");
            return (Criteria) this;
        }

        public Criteria andFilenameNotLike(String value) {
            addCriterion("filename not like", value, "filename");
            return (Criteria) this;
        }

        public Criteria andFilenameIn(List<String> values) {
            addCriterion("filename in", values, "filename");
            return (Criteria) this;
        }

        public Criteria andFilenameNotIn(List<String> values) {
            addCriterion("filename not in", values, "filename");
            return (Criteria) this;
        }

        public Criteria andFilenameBetween(String value1, String value2) {
            addCriterion("filename between", value1, value2, "filename");
            return (Criteria) this;
        }

        public Criteria andFilenameNotBetween(String value1, String value2) {
            addCriterion("filename not between", value1, value2, "filename");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table ads_album_gnere_year_nd
     *
     * @mbggenerated do_not_delete_during_merge Fri Jan 20 13:10:23 CST 2023
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table ads_album_gnere_year_nd
     *
     * @mbggenerated Fri Jan 20 13:10:23 CST 2023
     */
    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}