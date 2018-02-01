package ro.uaic.info.microservices;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import ro.uaic.info.classes.Comment;
import ro.wade.cryma.InternalDBInteractor.comment.CommentRetriever;

import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;

import java.sql.SQLException;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.QueryParam;

@Path("/comment")
public class AddCommentService {
	
	@POST
	@Path("/add")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addComent(Comment comment) {
		//Add the comment to the list of comments
		
		try {
			CommentRetriever comRet = new CommentRetriever();
			comRet.saveComment(comment.getCryptocurrencyId(), comment.getComment());
			comRet.closeConnection();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		System.out.println("The QueryParam from POST service call is: " + comment.toString());
		
		String result = "Test successfully !";
		
		return Response.status(200).entity(result).build();
	}

}