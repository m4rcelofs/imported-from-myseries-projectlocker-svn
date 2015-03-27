package se.kth.myseries.client.series.series;



import se.kth.myseries.client.MySeriesEP;
import se.kth.myseries.client.series.ISeriesServiceAsync;
import se.kth.myseries.shared.dto.SeriesDTO;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.layout.Portlet;

/**
 * MySeries <br>
 * @author jack
 * @since  2010-11-15
 */
public class StatisticsPorlet extends Portlet {
	private final  Long seriesID;
	private final  ISeriesServiceAsync	seriesService;
	
	/**
	 * @param id
	 * @param seriesService
	 */
	public StatisticsPorlet(final Long seriesID, final ISeriesServiceAsync seriesService) {
		this.setTitle("Statistics");
		this.setMargin(2);
		this.seriesID=seriesID;
		this.seriesService=seriesService;
		this.build();
	}
	
	
	private void build() {
		
		seriesService.getSeries(seriesID, MySeriesEP.getSignedRequest(),
				new AsyncCallback<SeriesDTO>() {
			
			private Label getLabel() {
				Label label = new Label();
				label.setAlign(Alignment.CENTER);
				label.setLayoutAlign(VerticalAlignment.CENTER);
				StatisticsPorlet.this.addItem(label);
				return label;
			}
			
			@Override
			public void onSuccess(final SeriesDTO result) {
				getLabel()
				.setContents(result.name+"test");
			}
			
			@Override
			public void onFailure(final Throwable caught) {
				getLabel().setContents(caught.getMessage());
			}
		});
	}
}
