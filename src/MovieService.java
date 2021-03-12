import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MovieService {
	
	public List<Movies> movieList;
	
	public List<Movies> populateMovies(String path) throws IOException {
		File f=new File(path);
		FileReader fr=new FileReader(f);
		BufferedReader br=new BufferedReader(fr);
		String line = "";
		
		List<Movies> finallist = new ArrayList<Movies>();
		while((line = br.readLine()) == null) {
			String arr[] = line.split(",");
			int movieid = Integer.parseInt(arr[0]);
			String movieName = arr[1];
			String movieType = arr[2];
			String language = arr[3];
			Date releaseDate = Date.valueOf(arr[4]);
			
			List<String> casting = new ArrayList<String>();
			String cast[] = arr[5].split(";");
			for(String c:cast) {
				casting.add(c);
			}
			Double rating = Double.parseDouble(arr[6]);
			Double totalBusinessDone = Double.parseDouble(arr[7]);
			
			Movies m = new Movies(movieid,movieName,movieType,language,releaseDate,casting,rating,totalBusinessDone);
			finallist.add(m);
		}
		return finallist;
	}
	
	Boolean allMoviesInDB(List<Movies> movies) {
		Connection cn = DBConnectionUtil.getConnection();
		
		
		for(Movies m: movies) {
			try {
				PreparedStatement pstmt = cn.prepareStatement("Insert into MoviesDet values(?,?,?,?,?,?,?,?)");
				pstmt.setInt(1, m.getMovieid());
				pstmt.setString(2, m.getMovieName());
				pstmt.setString(3, m.getMovieType());
				pstmt.setString(4, m.getLanguage());
				pstmt.setDate(5, m.getReleaseDate());
				String cast="";
				for(String s: m.getCasting()) {
					cast+=s;
				}
				pstmt.setString(6, cast);
				pstmt.setDouble(7, m.getRating());
				pstmt.setDouble(8, m.getTotalBusinessDone());
				
				pstmt.executeUpdate();
				pstmt.close();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}finally {
				DBConnectionUtil.closeConnection();
			}
			
		}
		return true;
	}
	
	public void addMovie(Movies movie,List<Movies>movielist) {
		movielist.add(movie);
	}
	
	public void serializeMovies(List<Movies>movielist,String filename) {
		try {
			FileOutputStream fout = new FileOutputStream(filename);
			ObjectOutputStream obj = new ObjectOutputStream(fout);
			obj.writeObject(movielist);
			obj.close();
			fout.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void deserializeMovie(String filename) {
		List<Movies> movielist = null;
		try
	    {
	        FileInputStream fis = new FileInputStream(filename);
	        ObjectInputStream ois = new ObjectInputStream(fis);

	        movielist = (ArrayList) ois.readObject();

	        ois.close();
	        fis.close();
	    } 
	    catch (IOException ioe) 
	    {
	        ioe.printStackTrace();
	        
	    } 
	    catch (ClassNotFoundException c) 
	    {
	        System.out.println("Class not found");
	       c.printStackTrace();
	    }
		
		for(Movies m: movielist) {
			System.out.println(m.getMovieName());
		}
	}
	
	List<Movies> getMoviesByYear(int year){
		List<Movies> movie_list = new ArrayList<Movies>();
		for(Movies m: this.movieList) {
			Date d = m.getReleaseDate();
			Calendar cal = Calendar.getInstance();
			cal.setTime(d);
			int y = cal.get(Calendar.YEAR);
			
			if(y == year) {
				movie_list.add(m);
			}
		}
		return movie_list;
	}
	
	public List<Movies> getMoviesByActor(String... actorNames){
		Set<Movies> movieset = new HashSet<Movies>();
		for(String actor:actorNames) {
			for(Movies m: this.movieList) {
				List<String> cast = m.getCasting();
				for(String c: cast) {
					if(c.equals(actor)) {
						movieset.add(m);
					}
				}
			}
		}
		
		List<Movies>finallist = new ArrayList<Movies>();
		finallist.addAll(movieset);
		return finallist;
	}
	
	public void updateRating(Movies movie, double rating, List<Movies> movies) {
		for(Movies m: movies) {
			if(m.equals(movie)) {
				m.setRating(rating);
			}
		}
	}
	
	public void updateBusiness(Movies movie, double amount, List<Movies> movies) {
		for(Movies m: movies) {
			if(m.equals(movie)) {
				m.setTotalBusinessDone(amount);
			}
		}
	}
	
	Map<String,Set<Movies>> businessDone(double amount){
		Map<String,Set<Movies>> result = new HashMap<String,Set<Movies>>();
		
		for(Movies m: this.movieList) {
			if(m.getTotalBusinessDone() > amount) {
				String lang = m.getLanguage();
				if(result.containsKey(lang)) {
					result.get(lang).add(m);
				}else {
					Set<Movies>temp = new HashSet<Movies>();
					temp.add(m);
					result.put(lang,temp);
				}
			}
		}
		return result;
	}
	
}
