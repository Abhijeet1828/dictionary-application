package com.distributed.project.dictionary.server.gui;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.ui.TextAnchor;
import org.jfree.data.category.DefaultCategoryDataset;

import com.distributed.project.dictionary.server.DictionaryServer;
import com.distributed.project.dictionary.server.utils.Constants;

/**
 * This class is used to create charts for response time and handle the state
 * change.
 * 
 * @author Abhijeet - 1278218
 *
 */
public class GraphPanel {

	protected DictionaryServer dictionaryServer;

	/**
	 * This method is used to initialize the dictionary server.
	 * 
	 * @param dictionaryServer
	 */
	public GraphPanel(DictionaryServer dictionaryServer) {
		this.dictionaryServer = dictionaryServer;
	}

	/**
	 * This method is used to create a bar chart with empty values at the start of
	 * the server.
	 * 
	 * @return
	 */
	public JFreeChart createChart() {
		// Creating a default data set
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();

		// Initializing the dataset with 0 for each action
		dataset.setValue(0, Constants.GRAPH_ROW_KEY, "SEARCH");
		dataset.setValue(0, Constants.GRAPH_ROW_KEY, "ADD");
		dataset.setValue(0, Constants.GRAPH_ROW_KEY, "UPDATE");
		dataset.setValue(0, Constants.GRAPH_ROW_KEY, "DELETE");

		// Returning JfreeChart with the necessary components
		return ChartFactory.createBarChart("Response Time Chart Of Clients", StringUtils.EMPTY, Constants.GRAPH_ROW_KEY,
				dataset, PlotOrientation.VERTICAL, false, true, false);
	}

	/**
	 * This method is called to refresh the data of response time graph whenever
	 * user clicks on the GRAPH panel.
	 * 
	 * @return
	 */
	public JFreeChart stateChange() {
		// Fetching the response time map for all client.
		Map<String, List<Long>> responseTimeMap = dictionaryServer.getResponseTimeMap();

		// If response time map is empty then return default chart.
		if (MapUtils.isEmpty(responseTimeMap)) {
			return createChart();
		}

		// Creating instance of default data set
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();

		// Fetching and averaging the response times for each action or else keep it at
		// 0
		dataset.setValue(responseTimeMap.containsKey(Constants.SEARCH)
				? responseTimeMap.get(Constants.SEARCH).stream().mapToDouble(a -> a).average().orElse(0)
				: 0, Constants.GRAPH_ROW_KEY, "SEARCH");

		dataset.setValue(responseTimeMap.containsKey(Constants.ADD)
				? responseTimeMap.get(Constants.ADD).stream().mapToDouble(a -> a).average().orElse(0)
				: 0, Constants.GRAPH_ROW_KEY, "ADD");

		dataset.setValue(responseTimeMap.containsKey(Constants.UPDATE)
				? responseTimeMap.get(Constants.UPDATE).stream().mapToDouble(a -> a).average().orElse(0)
				: 0, Constants.GRAPH_ROW_KEY, "UPDATE");

		dataset.setValue(responseTimeMap.containsKey(Constants.DELETE)
				? responseTimeMap.get(Constants.DELETE).stream().mapToDouble(a -> a).average().orElse(0)
				: 0, Constants.GRAPH_ROW_KEY, "DELETE");

		// Creating the JFreeChart with the necessary values
		JFreeChart chart = ChartFactory.createBarChart("Response Time Chart Of Clients", StringUtils.EMPTY,
				Constants.GRAPH_ROW_KEY, dataset, PlotOrientation.VERTICAL, false, true, false);

		// Adding the exact values on top of each bars for better understanding of the
		// values.
		CategoryItemRenderer renderer = ((CategoryPlot) chart.getPlot()).getRenderer();
		renderer.setDefaultItemLabelGenerator(new StandardCategoryItemLabelGenerator());
		renderer.setDefaultItemLabelsVisible(true);
		ItemLabelPosition position = new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12, TextAnchor.TOP_CENTER);
		renderer.setDefaultPositiveItemLabelPosition(position);

		// Returning the chart with values and other configuragtions
		return chart;
	}

}
