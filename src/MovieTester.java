import java.io.IOException;
import java.util.List;

public class MovieTester {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		MovieService ms = new MovieService();
		
		String path = "D:\\Training\\JAVA\\ELTP_Assignments\\Assignment9\\src\\MovieDetails.txt";
		ms.movieList = ms.populateMovies(path);
		
		ms.allMoviesInDB(ms.movieList);
		
		List<Movies>list = ms.getMoviesByYear(2009);
		System.out.println("Movie released in the year 2009");
		
		for(Movies m: list) {
			System.out.println(m.getMovieName());
		}
		
	}

}
