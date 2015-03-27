package se.kth.myseries.client.profiles.components;

import se.kth.myseries.shared.dto.UserDTO;

import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

/**
 * MySeries <br>
 * 
 * @author Marcelo
 * @since 19/11/2010
 */
public class UserInfoArea extends HLayout {
	
	private final Canvas	canvasPhoto;
	private final Img		photo;
	private final VLayout	nameLayout;
	private final Label		nameLabel;
	
	// TODO badges
	
	public UserInfoArea() {
		this.canvasPhoto = new Canvas();
		this.photo = new Img();
		this.nameLabel = new Label();
		this.nameLayout = new VLayout();
		
		this.build();
	}
	
	/**
	 * 
	 */
	private void build() {
		{
			this.addMember(canvasPhoto);
			canvasPhoto.addChild(photo);
		}
		{
			{
				// TODO config CSS to look better
				nameLabel.setLayoutAlign(VerticalAlignment.CENTER);
				nameLayout.addMember(nameLabel);
			}
			this.addMember(nameLayout);
		}
	}
	
	public void setUserInfo(final UserDTO user) {
		
		nameLabel.setContents(user.firstName + " " + user.lastName);
		photo.setSrc(user.getLargePictureURL());
		
	}
	
	/**
	 * @param caught
	 */
	public void setError(final Throwable caught) {
		nameLabel.setContents(caught.toString());
	}

}
