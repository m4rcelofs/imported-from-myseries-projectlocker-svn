package se.kth.myseries.client.series.search;

import se.kth.myseries.client.MySeriesEP;
import se.kth.myseries.client.series.ISeriesServiceAsync;
import se.kth.myseries.client.series.SeriesTab;
import se.kth.myseries.client.series.series.SeriesView;
import se.kth.myseries.client.series.seriesdatasource.SearchDS;
import se.kth.myseries.client.series.seriesdatasource.SeriesDS;
import se.kth.myseries.client.utils.navigation.Page;
import se.kth.myseries.shared.dto.SeriesDTO;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.TextBox;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tile.TileGrid;
import com.smartgwt.client.widgets.tile.events.RecordClickHandler;
import com.smartgwt.client.widgets.viewer.DetailFormatter;
import com.smartgwt.client.widgets.viewer.DetailViewerField;

/**
 * MySeries <br>
 * 
 * @author zhe
 * @since 2010-11-13
 */
public class SearchView extends VLayout implements Page {
	
	private final ISeriesServiceAsync	seriesService;
	private final HLayout				blanketLayout	= new HLayout();
	private final HLayout				hLayout			= new HLayout();
	private final HLayout				hLayout2		= new HLayout();
	private final SeriesTab				seriesTab;
	private final TileGrid				tileGrid		= new TileGrid();
	
	public SearchView(final ISeriesServiceAsync seriesService,
			final SeriesTab seriesTab) {
		this.seriesService = seriesService;
		this.seriesTab = seriesTab;
		build();
	}
	
	public void build() {
		
		this.setWidth100();
		// tileGrid
		tileGrid.setDataSource(new SeriesDS(seriesService));
		tileGrid.setTileHeight(200);
		tileGrid.setTileWidth(345);
		tileGrid.setTileMargin(15);
		tileGrid.setHeight(950);
		tileGrid.setAutoFetchData(true);
		tileGrid.setTitle("Series");
		tileGrid.setAnimateTileChange(true);
		DetailViewerField titleField = new DetailViewerField("name");
		/*DetailViewerField watchedField = new DetailViewerField(
		"watchedByCurrentUser");
		watchedField.setDetailFormatter(new DetailFormatter() {
			@Override
			public String format(final Object value, final Record record,
					final DetailViewerField field) {
				Boolean checkr = false;
				String returnResult = "Watched";
				if (checkr.equals(value))
					returnResult = "Unwatched";
				return returnResult;
			}
		});*/
		DetailViewerField pictureField = new DetailViewerField("pictureURL");
		tileGrid.setFields(titleField, pictureField);
		tileGrid.addRecordClickHandler(new RecordClickHandler() {
			
			@Override
			public void onRecordClick(
					final com.smartgwt.client.widgets.tile.events.RecordClickEvent event) {
				String idString = event.getRecord().getAttribute("id");
				long id = 0;
				boolean ok = true;
				try {
					id = Integer.parseInt(idString);
				} catch (NumberFormatException nfe) {
					System.out.println("id of the serie do not exist");
					ok = false;
				}
				if (ok)
					seriesService.getSeries(id, MySeriesEP.getSignedRequest(),
							new AsyncCallback<SeriesDTO>() {
						
						@Override
						public void onFailure(final Throwable caught) {
							// TODO do something to warn the user
						}
						
						@Override
						public void onSuccess(final SeriesDTO result) {
							SeriesView serie = new SeriesView(
									seriesTab, result, seriesService);
							seriesTab.addPage(serie);
							
						}
						
					});
			}
			
		});
		// Filter area
		/*
		 * for testing,remove later
		 * final DataSource dataSource = new XJSONDataSource();
		 * dataSource.setDataTransport(RPCTransport.SCRIPTINCLUDE);
		 * dataSource.setDataFormat(DSDataFormat.JSON);
		 * dataSource.setDataURL(
		 * "http://www.smartclient.com/smartgwt/showcase/data/dataIntegration/json/contactsData.js"
		 * );
		 * DataSourceTextField nameField = new DataSourceTextField("name",
		 * "name");
		 * nameField.setValueXPath("name");
		 * dataSource.setFields(nameField);
		 */
		/*
		 * final DynamicForm filterForm = new DynamicForm();
		 * filterForm.setIsGroup(true);
		 * filterForm.setGroupTitle("Filter");
		 * filterForm.setNumCols(6);
		 * filterForm.setAutoFocus(false);
		 * //TextItem nameTagItem = new TextItem("name");
		 * SliderItem rateItem = new SliderItem();
		 * rateItem.setTitle("Rating");
		 * rateItem.setMinValue(1);
		 * rateItem.setMaxValue(5);
		 * rateItem.setDefaultValue(5);
		 * rateItem.setHeight(50);
		 * rateItem.setOperator(OperatorId.LESS_THAN);
		 * CheckboxItem watchedItem = new CheckboxItem("watchedByCurrentUser");
		 * watchedItem.setTitle("Only series I watch");
		 * filterForm.setFields(nameTagItem,rateItem,watchedItem);
		 * filterForm.setDataSource(new SeriesDS(seriesService));
		 * filterForm.addItemChangedHandler(new ItemChangedHandler() {
		 * public void onItemChanged(ItemChangedEvent event) {
		 * tileGrid.fetchData(filterForm.getValuesAsCriteria());
		 * }
		 * });
		 */
		final TextBox nameTagItem = new TextBox();
		IButton filterButton = new IButton("Search");
		filterButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(final ClickEvent event) {
				tileGrid.setDataSource(new SearchDS(nameTagItem.getText(),
						seriesService));
				tileGrid.fetchData();
			}
		});
		filterButton.setAutoFit(true);
		
		IButton clearButton = new IButton("Show all");
		clearButton.setAutoFit(true);
		clearButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(final ClickEvent event) {
				tileGrid.setDataSource(new SeriesDS(seriesService));
				tileGrid.fetchData();
			}
		});
		blanketLayout.setHeight(10);
		hLayout.setHeight(50);
		hLayout.setMargin(2);
		hLayout.addMember(nameTagItem);
		hLayout.addMember(filterButton);
		hLayout2.addMember(clearButton);

		addMember(blanketLayout);
		addMember(hLayout);
		addMember(hLayout2);
		addMember(tileGrid);
	}
	
	@Override
	public String getName() {
		return "Search";
	}
	
	@Override
	public Canvas getCanvas() {
		return this;
	}
	
	@Override
	public Object getIdentifier() {
		return "_search_";
	}

}
