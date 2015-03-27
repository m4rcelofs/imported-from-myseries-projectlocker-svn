package se.kth.myseries.client.home.portlets;

import java.util.List;

import se.kth.myseries.client.series.ISeriesServiceAsync;
import se.kth.myseries.shared.dto.SerieDTO;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.HeaderControls;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.widgets.Label;

/**
 * MySeries <br>
 * A portet that shows the top ranked series
 * 
 * @author Marcelo
 * @since 07/10/2010
 */
public class TopSeriesPortlet extends APortlet {
	
	private final ISeriesServiceAsync	seriesService;
	
	public TopSeriesPortlet(final ISeriesServiceAsync seriesService) {
		super();
		this.seriesService = seriesService;
		this.buildContent();
	}
	
	/*
	 * (non-Javadoc)
	 * @see se.kth.myseries.client.home.APortlet#buildContent()
	 */
	@Override
	public void buildContent() {
		// TODO Auto-generated method stub
		
		seriesService.getSeries(new AsyncCallback<List<SerieDTO>>() {
			
			private Label getLabel() {
				Label label = new Label();
				label.setAlign(Alignment.CENTER);
				label.setLayoutAlign(VerticalAlignment.CENTER);
				TopSeriesPortlet.this.addItem(label);
				return label;
			}
			
			@Override
			public void onFailure(final Throwable caught) {
				getLabel().setContents(caught.getMessage());
			}
			
			@Override
			public void onSuccess(final List<SerieDTO> result) {
				String content = "";
				for (SerieDTO dto : result)
					content += dto.toString();
				getLabel().setContents(content);
			}
			
		});
		
	}
	
	/*
	 * (non-Javadoc)
	 * @see se.kth.myseries.client.home.APortlet#setTitle()
	 */
	@Override
	public void setTitle() {
		this.setTitle("Top Series");
		
	}
	
	@Override
	public Object[] getHeaders() {
		Object[] h = new Object[] { HeaderControls.HEADER_LABEL };
		
		return h;
	}
	
}
