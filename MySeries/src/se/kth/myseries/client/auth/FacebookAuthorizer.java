package se.kth.myseries.client.auth;

import se.kth.myseries.client.MySeriesEP;
import se.kth.myseries.shared.dto.FBGraphAddress;
import se.kth.myseries.shared.exceptions.AppNotAuthorizedException;
import se.kth.myseries.shared.exceptions.NotOnFacebookException;

import com.google.gwt.user.client.Window.Location;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Dialog;

/**
 * MySeries <br>
 * Handles user authentication and app authorization on Facebook
 * 
 * @author Marcelo
 * @since 15/10/2010
 */
public class FacebookAuthorizer {
	
	private final IFacebookAuthenticationServiceAsync	authService;
	private FBGraphAddress								graphAPI;
	
	public FacebookAuthorizer(
			final IFacebookAuthenticationServiceAsync authService) {
		this.authService = authService;
		
		authService.getGraphAddress(new AsyncCallback<FBGraphAddress>() {
			
			@Override
			public void onSuccess(final FBGraphAddress result) {
				graphAPI = result;
			}
			
			@Override
			public void onFailure(final Throwable caught) {
				Dialog d = new Dialog();
				d.setShowModalMask(true);
				SC.warn("Error", "Could not get API_Key from server", null, d);
			}
		});
	}
	
	public void authorize() {
		
		String signed_request = MySeriesEP.getSignedRequest();
		
		authService.defineSignedRequest(signed_request,
				new AsyncCallback<Void>() {
			
			@Override
			public void onSuccess(final Void result) {}
			
			@Override
			public void onFailure(final Throwable caught) {
				Dialog dialog = new Dialog();
				dialog.setShowModalMask(true);
				
				try {
					// just a convenient way to find out which exception
					// was thrown
					throw caught;
					
				} catch (NotOnFacebookException e) {
					// SC.warn("Error", e.getMessage()
					// + "<br />You will be redirected (:",
					// new BooleanCallback() {
					// @Override
					// public void execute(final Boolean value) {
					// if (value)
					// Location.assign("http://apps.facebook.com/myseries");
					// }
					// }, dialog);
					// FIXME for deployment
					
				} catch (AppNotAuthorizedException e) {
					
					replace(graphAPI.getFirstAuthorizationURL());
					
				} catch (Throwable e) {
					SC.warn("Error", e.getMessage()
							+ "<br />Page will be reloaded (:",
							new BooleanCallback() {
						@Override
						public void execute(final Boolean value) {
							if (value)
								Location.reload();
						}
					}, dialog);
				}
			}
		});
	}
	
	/**
	 * Assigns the parent window to a new URL. All GWT state will be lost. <br />
	 * If this method is called from outside an iFrame, who knows what will
	 * happen... :P
	 * 
	 * @param newURL
	 *            the new URL
	 */
	public static native void replace(String newURL) /*-{
		$wnd.parent.location.replace(newURL);
	}-*/;
	
}
