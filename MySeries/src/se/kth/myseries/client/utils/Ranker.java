/**
 * 
 */
package se.kth.myseries.client.utils;

import com.smartgwt.client.widgets.ImgButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;

/**
 * MySeries
 * 
 * @author Xu Liu
 * @since Nov 25, 2010
 */
public class Ranker extends HLayout {
	private Double	rankNum	= Double.valueOf(0);
	
	public Ranker() {
		
		this.setMembersMargin(3);
		
		this.setWidth(18 * 5 + 12);
		this.setHeight(18 + 2);
		
		this.addMember(getButton("Rank ", 1));
		this.addMember(getButton("Rank ", 2));
		this.addMember(getButton("Rank ", 3));
		this.addMember(getButton("Rank ", 4));
		this.addMember(getButton("Rank ", 5));
		
	}
	
	public Double getRankNum() {
		return rankNum;
	}
	
	private ImgButton getButton(final String tip, final Integer value) {
		return getButton(tip, Double.valueOf(value));
	}

	private ImgButton getButton(final String tip, final Double value) {
		ImgButton button = new ImgButton();
		{
			button.setWidth(18);
			button.setHeight(18);
			button.setShowRollOver(true);
			button.setShowDown(true);
			button.setSrc("button.png");
			button.setTooltip(tip);
			button.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(final ClickEvent event) {
					rankNum = value;
				}
			});
		}
		return button;
	}
}
