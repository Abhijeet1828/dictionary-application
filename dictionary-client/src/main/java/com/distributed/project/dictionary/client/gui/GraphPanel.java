package com.distributed.project.dictionary.client.gui;

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

import com.distributed.project.dictionary.client.DictionaryClient;
import com.distributed.project.dictionary.client.utils.Constants;
import com.distributed.project.dictionary.client.utils.TabPaneEnum;

/**
 * This class is used to generate response time graph charts and display it on
 * the dicttionary client UI.
 * 
 * 
 * @author Abhijeet - 1278218
 *
 */
public class GraphPanel {

	protected DictionaryClient dictionaryClient;

	/**
	 * This constructor is used to intialize the dictionary client.
	 * 
	 * @param dictionaryClient
	 */
	public GraphPanel(DictionaryClient dictionaryClient) {
		this.dictionaryClient = dictionaryClient;
	}

	/**
	 * This method is used to create an initial chart with values for all operations
	 * at zero.
	 * 
	 * @return
	 */
	public JFreeChart createChart() {
		// Creating default data set
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();

		// Setting values at 0 for all operations
		dataset.setValue(0, Constants.GRAPH_ROW_KEY, TabPaneEnum.SEARCH.getPaneName());
		dataset.setValue(0, Constants.GRAPH_ROW_KEY, TabPaneEnum.ADD.getPaneName());
		dataset.setValue(0, Constants.GRAPH_ROW_KEY, TabPaneEnum.UPDATE.getPaneName());
		dataset.setValue(0, Constants.GRAPH_ROW_KEY, TabPaneEnum.DELETE.getPaneName());

		// Creating chart with the dataset and other properties
		return ChartFactory.createBarChart("Response Time Chart", StringUtils.EMPTY, Constants.GRAPH_ROW_KEY, dataset,
				PlotOrientation.VERTICAL, false, true, false);
	}

	/**
	 * This method is called when the user clicks on the graph panel. It calculates
	 * the average of response times for each operation.
	 * 
	 * @return
	 */
	public JFreeChart stateChange() {
		// Fetching the response time map
		Map<String, List<Long>> responseTimeMap = dictionaryClient.getResponseTimeMap();

		// If response time map is empty then return initial chart
		if (MapUtils.isEmpty(responseTimeMap)) {
			return createChart();
		}

		// Creating default data set
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();

		// Averaging the response times for each operation or else setting the value at
		// zero
		dataset.setValue(responseTimeMap.containsKey(Constants.SEARCH)
				? responseTimeMap.get(Constants.SEARCH).stream().mapToDouble(a -> a).average().orElse(0)
				: 0, Constants.GRAPH_ROW_KEY, TabPaneEnum.SEARCH.getPaneName());

		dataset.setValue(responseTimeMap.containsKey(Constants.ADD)
				? responseTimeMap.get(Constants.ADD).stream().mapToDouble(a -> a).average().orElse(0)
				: 0, Constants.GRAPH_ROW_KEY, TabPaneEnum.ADD.getPaneName());

		dataset.setValue(responseTimeMap.containsKey(Constants.UPDATE)
				? responseTimeMap.get(Constants.UPDATE).stream().mapToDouble(a -> a).average().orElse(0)
				: 0, Constants.GRAPH_ROW_KEY, TabPaneEnum.UPDATE.getPaneName());

		dataset.setValue(responseTimeMap.containsKey(Constants.DELETE)
				? responseTimeMap.get(Constants.DELETE).stream().mapToDouble(a -> a).average().orElse(0)
				: 0, Constants.GRAPH_ROW_KEY, TabPaneEnum.DELETE.getPaneName());

		// Creating chart with data set and necessary properties
		JFreeChart chart = ChartFactory.createBarChart("Response Time Chart", StringUtils.EMPTY,
				Constants.GRAPH_ROW_KEY, dataset, PlotOrientation.VERTICAL, false, true, false);

		// Adding the exact values at top of each bar chart.
		CategoryItemRenderer renderer = ((CategoryPlot) chart.getPlot()).getRenderer();
		renderer.setDefaultItemLabelGenerator(new StandardCategoryItemLabelGenerator());
		renderer.setDefaultItemLabelsVisible(true);
		ItemLabelPosition position = new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12, TextAnchor.TOP_CENTER);
		renderer.setDefaultPositiveItemLabelPosition(position);

		// Returning the generated chart
		return chart;
	}
}
