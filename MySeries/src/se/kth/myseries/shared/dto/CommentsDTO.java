package se.kth.myseries.shared.dto;

import java.io.Serializable;
import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * MySeries <br>
 * 
 * @author Marcelo
 * @since 25/11/2010
 */
public class CommentsDTO implements IsSerializable, Serializable {
	
	private static final long		serialVersionUID	= 1L;
	
	/**
	 * The total number of pages containing comments on the server
	 */
	public Integer					totalNumberOfPages;
	
	/**
	 * The page's number on witch these comments are
	 */
	public Integer					currentPage;
	
	public List<CommentsContents>	comments;
	
	/**
	 * @return If this is the last page of comments available on the server
	 */
	public boolean isLastPage() {
		return this.currentPage == this.totalNumberOfPages;
	}

	public class CommentsContents implements IsSerializable, Serializable {
		
		private static final long	serialVersionUID	= 1L;
		
		public Long					id;
		
		/**
		 * Will only have some attributes set
		 */
		public UserDTO				user;
		
		public String				text;
		
		/**
		 * yyyyMMddHHmm
		 */
		public Integer				date;
	}
}
