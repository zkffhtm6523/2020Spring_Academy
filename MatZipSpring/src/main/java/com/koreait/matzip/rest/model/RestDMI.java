package com.koreait.matzip.rest.model;

public class RestDMI extends RestVO{
	private String userNm;
	private String cd_category_nm;
	private int hits;
	private int cntFavorite;
	private int is_favorite;
	
	public int getIs_favorite() {
		return is_favorite;
	}
	public void setIs_favorite(int is_favorite) {
		this.is_favorite = is_favorite;
	}
	public int getHits() {
		return hits;
	}
	public void setHits(int hits) {
		this.hits = hits;
	}
	public String getUserNm() {
		return userNm;
	}
	public void setUserNm(String userNm) {
		this.userNm = userNm;
	}

	public int getCntFavorite() {
		return cntFavorite;
	}
	public void setCntFavorite(int cntFavorite) {
		this.cntFavorite = cntFavorite;
	}
	public String getCd_category_nm() {
		return cd_category_nm;
	}
	public void setCd_category_nm(String cd_category_nm) {
		this.cd_category_nm = cd_category_nm;
	}
}
