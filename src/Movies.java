import java.io.Serializable;
import java.sql.Date;
import java.util.List;

public class Movies implements Serializable, Comparable<Movies>{
	private int movieid;
	private String movieName;
	private String movieType;
	private String language;
	private Date releaseDate;
	private List<String> casting;
	private Double rating;
	private Double totalBusinessDone;
	
	Movies(){
		
	}
	
	public Movies(int movieid, String movieName, String movieType, String language, Date releaseDate,
			List<String> casting, Double rating, Double totalBusinessDone) {
		super();
		this.movieid = movieid;
		this.movieName = movieName;
		this.movieType = movieType;
		this.language = language;
		this.releaseDate = releaseDate;
		this.casting = casting;
		this.rating = rating;
		this.totalBusinessDone = totalBusinessDone;
	}

	@Override
	public String toString() {
		return "Movies [movieid=" + movieid + ", movieName=" + movieName + ", movieType=" + movieType + ", language="
				+ language + ", releaseDate=" + releaseDate + ", casting=" + casting + ", rating=" + rating
				+ ", totalBusinessDone=" + totalBusinessDone + "]";
	}

	public int getMovieid() {
		return movieid;
	}

	public void setMovieid(int movieid) {
		this.movieid = movieid;
	}

	public String getMovieName() {
		return movieName;
	}

	public void setMovieName(String movieName) {
		this.movieName = movieName;
	}

	public String getMovieType() {
		return movieType;
	}

	public void setMovieType(String movieType) {
		this.movieType = movieType;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public Date getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}

	public List<String> getCasting() {
		return casting;
	}

	public void setCasting(List<String> casting) {
		this.casting = casting;
	}

	public Double getRating() {
		return rating;
	}

	public void setRating(Double rating) {
		this.rating = rating;
	}

	public Double getTotalBusinessDone() {
		return totalBusinessDone;
	}

	public void setTotalBusinessDone(Double totalBusinessDone) {
		this.totalBusinessDone = totalBusinessDone;
	}
	
	
	@Override
	public int compareTo(Movies o) {
		// TODO Auto-generated method stub
		if(this.getMovieid() < o.getMovieid()) return 1;
		return 0;
	}
}
