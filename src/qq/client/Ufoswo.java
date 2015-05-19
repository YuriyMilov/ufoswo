package qq.client;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.maps.client.InfoWindowContent;
import com.google.gwt.maps.client.MapType;

import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.maps.client.control.LargeMapControl;

import com.google.gwt.maps.client.event.InfoWindowCloseClickHandler;
import com.google.gwt.maps.client.event.MarkerClickHandler;
import com.google.gwt.maps.client.geocode.GeocodeCache;
import com.google.gwt.maps.client.geocode.Geocoder;
import com.google.gwt.maps.client.geocode.LatLngCallback;
import com.google.gwt.maps.client.geom.LatLng;
import com.google.gwt.maps.client.geom.Point;
import com.google.gwt.maps.client.geom.Size;
import com.google.gwt.maps.client.overlay.Icon;
import com.google.gwt.maps.client.overlay.Marker;
import com.google.gwt.maps.client.overlay.MarkerOptions;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import qq.client.Service;
import qq.client.ServiceAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DecoratedTabPanel;
import com.google.gwt.user.client.ui.DecoratorPanel;

import com.google.gwt.user.client.ui.FlexTable;

import com.google.gwt.user.client.ui.ListBox;

import com.google.gwt.user.client.ui.TextBox;

import com.google.gwt.user.client.ui.FlexTable.FlexCellFormatter;
import com.google.gwt.user.datepicker.client.DateBox;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Ufoswo implements EntryPoint {

	private final ServiceAsync service = GWT.create(Service.class);
	String slog = "<center><FORM METHOD=POST ACTION=\"login\"><BR><input type=\"submit\" name=\"login\" value=\"Log In\"></p></form></center></body></html>";
	String user_email = "";

	public void onModuleLoad() {
		final String sdemo = "";
		final DecoratedTabPanel tabPanel = new DecoratedTabPanel();
		tabPanel.setAnimationEnabled(true);
		// final VerticalPanel vp_ifr = new VerticalPanel();
		final VerticalPanel vp_map = new VerticalPanel();
		final VerticalPanel vp_addr = new VerticalPanel();

		final HorizontalPanel hp_map = new HorizontalPanel();
		vp_map.add(hp_map);
		final MapWidget map1 = new MapWidget();
		map1.setContinuousZoom(true);
		map1.setScrollWheelZoomEnabled(false);
		map1.setSize("700px", "444px");
		map1.addControl(new LargeMapControl());
		map1.setCenter(LatLng.newInstance(42.6898961957299, -71.1611878218555));
		map1.setZoomLevel(8);
		initMarkers(map1);

		final MapWidget map2 = new MapWidget();

		final HTML htm1 = new HTML("Trucks");
		final HTML htm2 = new HTML("Shippers");
		final HTML ifrm = new HTML();
		ifrm.setHTML("<iframe src =/a width=700 height=444></iframe>");
		vp_map.add(ifrm);

		hp_map.add(new Button("Trucks", new ClickHandler() {
			public void onClick(ClickEvent event) {
				vp_map.remove(ifrm);
				vp_map.remove(map2);
				vp_map.add(map1);
				vp_map.remove(htm2);
				vp_map.add(htm1);
			}

		}));
		hp_map.add(new Button("Shippers", new ClickHandler() {

			public void onClick(ClickEvent event) {
				
				map2.setContinuousZoom(true);
				map2.setScrollWheelZoomEnabled(false);
				map2.setSize("700px", "444px");
				map2.addControl(new LargeMapControl());
				map2.setCenter(LatLng.newInstance(42.6898961957299, -71.1611878218555));
				map2.setZoomLevel(7);
				initMarkers2(map2);
				
				
				vp_map.remove(ifrm);
				vp_map.remove(map1);
				vp_map.remove(htm1);
				vp_map.add(map2);
				vp_map.add(htm2);
				
				
			}

		}));

		hp_map.add(new Button("Markers", new ClickHandler() {

			public void onClick(ClickEvent event) {
				vp_map.remove(map2);
				vp_map.remove(map1);
				vp_map.remove(htm1);
				vp_map.remove(htm2);
				vp_map.add(ifrm);
			}

		}));
		
		final Label res = new Label();
		res.setText("Enter address:");
		final Geocoder gg = new Geocoder();
		final MapWidget map3 = new MapWidget();
		map3.setContinuousZoom(true);
		map3.setScrollWheelZoomEnabled(false);
		map3.setSize("700px", "444px");
		map3.addControl(new LargeMapControl());
		map3.setCenter(LatLng.newInstance(42.6898961957299, -71.1611878218555));
		map3.setZoomLevel(5);
		final TextBox tb= new TextBox();
		tb.setText("Toronto");
		vp_addr.add(res);
		vp_addr.add(tb);
		final ListBox listBox_mar = new ListBox();
		service.get_list("Shipper", new AsyncCallback<String[]>() {
			public void onFailure(Throwable caught) {
			}

			public void onSuccess(String listTypes[]) {
				for (int i = 0; i < listTypes.length; i++) {
					listBox_mar.addItem(listTypes[i], listTypes[i + 1]);
					i++;
				}
			}
		});
		vp_addr.add(listBox_mar);

		vp_addr.add(new Button("Add marker by address", new ClickHandler() {
			Marker mar = null;
			
			
			public void onClick(ClickEvent event) {
				gg.getLatLng(tb.getText(), new LatLngCallback() {
					public void onFailure() {
						res.setText("Unable to geocode");
					}

					public void onSuccess(LatLng point) {
						map3.removeOverlay(mar);
						mar=new Marker(point);
						map3.addOverlay(mar);
						
					}
				});
			}
		}));
		
		vp_addr.add(map3);
		
		
		//tabPanel.add(vp_map, "Markers by coordinates");
		//tabPanel.add(vp_addr, "Markers by address");

		final VerticalPanel vp = new VerticalPanel();

		service.get_user("", new AsyncCallback<String>() {
			public void onFailure(Throwable caught) {
				vp.add(new HTML(caught.toString()));
			}

			public void onSuccess(String result) {
				user_email = result;
				service.get_user("", new AsyncCallback<String>() {
					public void onFailure(Throwable caught) {
						vp.add(new HTML(slog));
					}

					public void onSuccess(String result) {
						if (result.indexOf("<!--ymqq-->") > -1) {

							HTML ht = new HTML(result);
							vp.setPixelSize(444, 333);
							vp.add(ht);

						} else {

							VerticalPanel dialogContents = new VerticalPanel();

							VerticalPanel vPanel_nwo = new_wo();

							dialogContents.add(vPanel_nwo);
							dialogContents.setCellHorizontalAlignment(
									vPanel_nwo,
									HasHorizontalAlignment.ALIGN_CENTER);
							vp.add(dialogContents);

							service.get("new_shipper_templ.htm",
									new AsyncCallback<String>() {
										public void onFailure(Throwable caught) {
										}

										public void onSuccess(String result3) {
											HTML homeText3 = new HTML(result3);
											tabPanel.add(homeText3,
													"New Shipper");
										}
									});
							service.get("new_cons_templ.htm",
									new AsyncCallback<String>() {
										public void onFailure(Throwable caught) {
										}

										public void onSuccess(String result4) {
											HTML homeText4 = new HTML(result4);
											tabPanel.add(homeText4,
													"New Consignee");
										}
									});

							VerticalPanel vp2 = new VerticalPanel();
							vp2
									.add(new HTML(
											"<br/>"
													+ "To import the entered work order: <br/><br/><br/>"
													+ "Unzip <a href=http://www.quicklydone.com/webor.rar><b>"
													+ "'webor.rar' archive</b></a> (2 files) in the folder "
													+ "where UFOS has been installed<br/>"
													+ " (DATA folder should be located there).<br/><br/>"
													+ "Write an email address of your Google account in the first line of the file \"webor_id.txt\"<br/><br/>"
													+ "To check/import a new order run 'webor.exe' <br/><br/>"
													+ "See log in the same folder if any problem<br/><br/><br/>"));
							tabPanel.add(vp2, "Import");
						}

					}
				});

			}

		});
		tabPanel.add(vp, "Work Order");

		tabPanel.selectTab(0);
		RootPanel.get().add(tabPanel);

	}

	// ///////////////////////////////////////////////
	// ///////////////////////////////////////////////
	// ///////////////////////////////////////////////

	private VerticalPanel new_wo() {
		final VerticalPanel vPanel_nwo = new VerticalPanel();
		final ListBox dropBox_shipper = new ListBox(false);
		final ListBox dropBox_consignee = new ListBox(false);
		final DateBox dateBox_ship = new DateBox();
		final DateBox dateBox_cons = new DateBox();
		final FlexTable layout = new FlexTable();
		layout.setCellSpacing(3);
		final TextBox po_id = new TextBox();

		layout.setHTML(0, 0, "Purchase&nbsp;Order&nbsp;ID: ");
		layout.setWidget(0, 1, po_id);

		final TextBox description_tb = new TextBox();
		layout.setHTML(0, 2, "Order&nbsp;Description: ");
		layout.setWidget(0, 3, description_tb);

		service.get_list("Shipper", new AsyncCallback<String[]>() {
			public void onFailure(Throwable caught) {
			}

			public void onSuccess(String listTypes[]) {
				for (int i = 0; i < listTypes.length; i++) {
					dropBox_shipper.addItem(listTypes[i], listTypes[i + 1]);
					i++;
				}
			}
		});

		service.get_list("Consignee", new AsyncCallback<String[]>() {
			public void onFailure(Throwable caught) {
			}

			public void onSuccess(String listTypes[]) {
				for (int i = 0; i < listTypes.length; i++) {
					dropBox_consignee.addItem(listTypes[i], listTypes[i + 1]);
					i++;
				}
			}
		});

		layout.setHTML(1, 0, "Shipper: ");
		layout.setWidget(1, 1, dropBox_shipper);

		layout.setHTML(1, 2, "Consignee: ");
		layout.setWidget(1, 3, dropBox_consignee);

		layout.setHTML(2, 0, "Shipping&nbsp;Date/Time: ");
		layout.setWidget(2, 1, dateBox_ship);
		layout.setHTML(2, 2, "Delivery&nbsp;Date/Time: ");
		layout.setWidget(2, 3, dateBox_cons);

		final ListBox dropBox_equipment_type = new ListBox(false);

		service.get_array("et", new AsyncCallback<String[]>() {
			public void onFailure(Throwable caught) {
			}

			public void onSuccess(String listTypes[]) {
				for (int i = 0; i < listTypes.length; i++) {
					dropBox_equipment_type.addItem(listTypes[i]);
				}
			}
		});

		final ListBox dropBox_shipment_type = new ListBox(false);

		service.get_array("st", new AsyncCallback<String[]>() {
			public void onFailure(Throwable caught) {
			}

			public void onSuccess(String listTypes[]) {
				for (int i = 0; i < listTypes.length; i++) {
					dropBox_shipment_type.addItem(listTypes[i]);
				}
			}
		});

		layout.setHTML(3, 0, "Equipment&nbsp;type: ");
		layout.setWidget(3, 1, dropBox_equipment_type);
		layout.setHTML(3, 2, "Shipment&nbsp;type: ");
		layout.setWidget(3, 3, dropBox_shipment_type);

		final TextBox weight_tb = new TextBox();
		final ListBox weight_unit_db = new ListBox(false);
		weight_unit_db.addItem("kg");
		weight_unit_db.addItem("lb");
		FlexTable ft2 = new FlexTable();
		ft2.setWidget(0, 0, weight_tb);
		ft2.setWidget(0, 1, weight_unit_db);
		layout.setHTML(4, 0, "Weight: ");
		layout.setWidget(4, 1, ft2);

		final TextBox quantity_tb = new TextBox();
		layout.setHTML(4, 2, "Quantity: ");
		layout.setWidget(4, 3, quantity_tb);

		final TextBox container_id_tb = new TextBox();

		layout.setHTML(5, 0, "Conainer&nbsp;ID: ");
		layout.setWidget(5, 1, container_id_tb);

		final TextBox bill_of_lading_tb = new TextBox();
		layout.setHTML(5, 2, "Bill&nbsp;of&nbsp;Lading: ");
		layout.setWidget(5, 3, bill_of_lading_tb);

		final TextBox special_instructions_tb = new TextBox();

		layout.setHTML(6, 0, "Special&nbsp;instructions: ");
		layout.setWidget(6, 1, special_instructions_tb);
		layout.setHTML(6, 2, "Customer: ");
		layout.setHTML(6, 3, user_email);
		final DecoratorPanel decPanel = new DecoratorPanel();
		decPanel.setTitle("Entry Form");

		Button buttonSubmit = new Button("Submit", new ClickHandler() {
			public void onClick(ClickEvent event) {

				layout.setHTML(6, 2, "Wait...");

				service.get_new(po_id.getText(), dropBox_shipper
						.getValue(dropBox_shipper.getSelectedIndex()),
						dropBox_consignee.getValue(dropBox_consignee
								.getSelectedIndex()), dateBox_ship.getValue(),
						dateBox_cons.getValue(), description_tb.getText(),
						dropBox_equipment_type
								.getItemText(dropBox_equipment_type
										.getSelectedIndex()),
						dropBox_shipment_type.getItemText(dropBox_shipment_type
								.getSelectedIndex()), quantity_tb.getText(),
						weight_tb.getText(),
						weight_unit_db.getItemText(weight_unit_db
								.getSelectedIndex()),
						container_id_tb.getText(), bill_of_lading_tb.getText(),
						special_instructions_tb.getText(),
						new AsyncCallback<String>() {
							public void onFailure(Throwable caught) {
							}

							public void onSuccess(String result) {
								layout.setHTML(6, 2, result);
								layout.setHTML(6, 3, "<font color=blue>"
										+ user_email
										+ "</font><br/>will be notified");

							}
						});

			}
		});

		layout.setWidget(7, 2, buttonSubmit);
		layout.setWidget(7, 3, new HTML("<a href=logout>Sign out<a>"));

		// Wrap the content in a DecoratorPanel
		FlexCellFormatter cellFormatter = layout.getFlexCellFormatter();
		cellFormatter.setHorizontalAlignment(0, 0,
				HasHorizontalAlignment.ALIGN_RIGHT);
		cellFormatter.setHorizontalAlignment(1, 0,
				HasHorizontalAlignment.ALIGN_RIGHT);
		cellFormatter.setHorizontalAlignment(2, 0,
				HasHorizontalAlignment.ALIGN_RIGHT);
		cellFormatter.setHorizontalAlignment(3, 0,
				HasHorizontalAlignment.ALIGN_RIGHT);
		cellFormatter.setHorizontalAlignment(4, 0,
				HasHorizontalAlignment.ALIGN_RIGHT);
		cellFormatter.setHorizontalAlignment(5, 0,
				HasHorizontalAlignment.ALIGN_RIGHT);
		cellFormatter.setHorizontalAlignment(6, 0,
				HasHorizontalAlignment.ALIGN_RIGHT);

		cellFormatter.setHorizontalAlignment(0, 2,
				HasHorizontalAlignment.ALIGN_RIGHT);
		cellFormatter.setHorizontalAlignment(1, 2,
				HasHorizontalAlignment.ALIGN_RIGHT);
		cellFormatter.setHorizontalAlignment(2, 2,
				HasHorizontalAlignment.ALIGN_RIGHT);
		cellFormatter.setHorizontalAlignment(3, 2,
				HasHorizontalAlignment.ALIGN_RIGHT);
		cellFormatter.setHorizontalAlignment(4, 2,
				HasHorizontalAlignment.ALIGN_RIGHT);
		cellFormatter.setHorizontalAlignment(5, 2,
				HasHorizontalAlignment.ALIGN_RIGHT);
		cellFormatter.setHorizontalAlignment(6, 2,
				HasHorizontalAlignment.ALIGN_RIGHT);

		decPanel.setWidget(layout);
		vPanel_nwo.add(decPanel);
		return vPanel_nwo;
	}

	private Map<String, LatLng> initLocs1() {
		HashMap<String, LatLng> locs = new HashMap<String, LatLng>();
		locs
				.put("001", LatLng.newInstance(41.2558681353242,
						-70.8152475780165));
		locs.put("010", LatLng.newInstance(42.212301588702, -72.6411211406311));
		locs
				.put("011", LatLng.newInstance(42.1170983094661,
						-72.5479789187525));
		locs
				.put("012", LatLng.newInstance(42.2805009057921,
						-73.2070179770502));
		locs
				.put("013", LatLng.newInstance(42.6099024760077,
						-72.4211022247452));
		locs
				.put("023", LatLng.newInstance(42.0560003625449,
						-70.8723659993611));
		locs.put("024", LatLng.newInstance(42.3311431237047, -71.255936541659));
		locs.put("025", LatLng.newInstance(41.527799429217, -70.6629795945453));
		locs
				.put("026", LatLng.newInstance(41.6709403777211,
						-70.0727587919982));
		locs.put("027", LatLng.newInstance(41.667849144924, -70.8173767564175));

		return locs;
	}

	private Map<String, LatLng> initLocs2() {
		HashMap<String, LatLng> locs = new HashMap<String, LatLng>();

		locs.put("014", LatLng.newInstance(42.5351046840491, -71.488823946219));
		locs.put("015", LatLng.newInstance(42.318900636606, -72.1294472483989));
		locs.put("016", LatLng.newInstance(42.227204559281, -71.7873132074038));
		locs
				.put("017", LatLng.newInstance(42.2253562723998,
						-71.5379156217244));
		locs
				.put("018", LatLng.newInstance(42.6898961957299,
						-71.1611878218555));
		locs
				.put("019", LatLng.newInstance(42.5819438054134,
						-70.7692820289472));
		locs
				.put("020", LatLng.newInstance(42.1846288457393,
						-71.3049781598891));
		locs.put("021", LatLng.newInstance(42.393528203942, -71.133795067936));
		locs
				.put("022", LatLng.newInstance(42.3474743983872,
						-71.1016113746733));

		return locs;
	}

	private MarkerOptions makeMarkerOptions(final String locationLabel) {
		final MarkerOptions options = MarkerOptions.newInstance();
		options.setTitle(locationLabel);
		options.setClickable(true);
		options.setIcon(makeIcon());
		return options;
	}

	private Icon makeIcon() {
		final Icon icon = Icon.newInstance();
		final String imageURL = MarkerConstants.imageURL;

		icon.setImageURL(imageURL);
		icon.setShadowURL(MarkerConstants.shadowImageURL);
		icon.setShadowSize(MarkerConstants.defaultShadowSize);
		icon.setIconAnchor(MarkerConstants.defaultIconAnchor);
		icon.setInfoWindowAnchor(MarkerConstants.defaultInfoWindowAnchor);

		return icon;
	}

	private static final class MarkerConstants {
		private static final String imageURL = "http://www.google.com/intl/en_us/mapfiles/ms/micons/red-dot.png";
		private static final String shadowImageURL = "http://www.google.com/mapfiles/shadow50.png";
		private static final Size defaultShadowSize = Size.newInstance(37, 34);
		private static final Point defaultIconAnchor = Point.newInstance(9, 34);
		private static final Point defaultInfoWindowAnchor = Point.newInstance(
				15, 32);
	}

	private MarkerClickHandler makeMarkerClickHandler(final MapWidget map,
			final String locationLabel) {
		return new MarkerClickHandler() {
			public void onClick(final MarkerClickEvent event) {
				map.savePosition();
				final VerticalPanel infoWindowPanel = displayDataForLocation(locationLabel);

				map.getInfoWindow().open(event.getSender(),
						new InfoWindowContent(infoWindowPanel));
				map.getInfoWindow().getPixelOffset();
			}

			private VerticalPanel displayDataForLocation(
					final String locationLabel) {
				final HorizontalPanel headerPanel = new HorizontalPanel();
				headerPanel.add(new Image(MarkerConstants.imageURL));

				headerPanel.add(new Label(locationLabel));
				// headerPanel.add(new HTML("<b>" + locationLabel + "</b> "));

				final VerticalPanel infoWindowPanel = new VerticalPanel();
				infoWindowPanel.add(headerPanel);
				// infoWindowPanel.setHeight("200px");
				// infoWindowPanel.setWidth("300px");
				infoWindowPanel.setHeight("100px");
				infoWindowPanel.setWidth("300px");

				final VerticalPanel columns = new VerticalPanel();
				columns.add(new Label("Percentage: 4.0  Low threshold: 3.0"));
				columns.add(new Label("Expected: 2.0  High threshold: 4.5"));

				infoWindowPanel.add(columns);

				return infoWindowPanel;
			}
		};
	}

	private static void addInfoWindowCloseClickHandler(final MapWidget map) {
		map.getInfoWindow().addInfoWindowCloseClickHandler(
				new InfoWindowCloseClickHandler() {
					public void onCloseClick(
							final InfoWindowCloseClickEvent event) {
						map.returnToSavedPosition();
					}
				});
	}

	private void initMarkers(MapWidget map) {
		Collection<Marker> markers = new ArrayList<Marker>();
		Map<String, LatLng> locs = initLocs1();
		for (String locationLabel : locs.keySet()) {
			final MarkerOptions options = makeMarkerOptions(locationLabel);
			LatLng coords = locs.get(locationLabel);
			final Marker marker = new Marker(coords, options);
			marker.addMarkerClickHandler(makeMarkerClickHandler(map,
					locationLabel));
			markers.add(marker);
			map.addOverlay(marker);
		}

	}

	private void initMarkers2(MapWidget map) {
		Collection<Marker> markers = new ArrayList<Marker>();
		Map<String, LatLng> locs = initLocs2();
		for (String locationLabel : locs.keySet()) {
			final MarkerOptions options = makeMarkerOptions(locationLabel);
			LatLng coords = locs.get(locationLabel);
			final Marker marker = new Marker(coords, options);
			marker.addMarkerClickHandler(makeMarkerClickHandler(map,
					locationLabel));
			markers.add(marker);
			map.addOverlay(marker);
		}

	}

}
