//package cn.robotium.utils;
//
//import java.awt.Color;
//import java.awt.Font;
//import java.io.BufferedReader;
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.io.FileReader;
//import java.io.IOException;
//import java.text.DecimalFormat;
//import java.text.NumberFormat;
//
//import org.jfree.chart.ChartFactory;
//import org.jfree.chart.ChartUtilities;
//import org.jfree.chart.JFreeChart;
//import org.jfree.chart.axis.CategoryAxis;
//import org.jfree.chart.axis.CategoryLabelPositions;
//import org.jfree.chart.axis.NumberAxis;
//import org.jfree.chart.axis.ValueAxis;
//import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
//import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
//import org.jfree.chart.plot.CategoryPlot;
//import org.jfree.chart.plot.PiePlot3D;
//import org.jfree.chart.plot.PlotOrientation;
//import org.jfree.chart.renderer.category.BarRenderer;
//import org.jfree.chart.renderer.category.LineAndShapeRenderer;
//import org.jfree.chart.renderer.category.StackedBarRenderer;
//import org.jfree.chart.title.TextTitle;
//import org.jfree.data.category.CategoryDataset;
//import org.jfree.data.general.DatasetUtilities;
//import org.jfree.data.general.DefaultPieDataset;
//import org.jfree.data.general.PieDataset;
//
///**
// * ʵ��ȡɫ��ʱ��һ��Ҫ16λ�ģ�����Ƚ�׼ȷ
// * 
// * @author new
// */
//public class CreateChartServiceImpl {
//	
//	//private static final String CHART_PATH = "D:" + File.separator + "Memory" + File.separator;
//	public BufferedReader br;
//	public String line;
//	public String[] list;
//	public double[] nativeSizeArray = null;
//	public double[] dalvikSizeArray = null;
//	public double[] totalSizeArray = null;
//    
//	public double[] nativeAllocatedArray = null;
//	public double[] dalvikAllocatedArray = null;
//	public double[] totalAllocatedArray = null;
//    
//	public double[] nativePSSArray = null;
//	public double[] dalvikPSSArray = null;
//	public double[] totalPSSArray = null;
//
//    private static final String CHART_PATH = "E:/test/";
//
//    public static void main(String[] args) {
//        // TODO Auto-generated method stub
//        CreateChartServiceImpl pm = new CreateChartServiceImpl();
////        // ��ɱ�״ͼ
////        pm.makePieChart();
////        // ��ɵ�����״ͼ
////        pm.makeBarChart();
////        // ��ɶ�����״ͼ
////        pm.makeBarGroupChart();
////        // ��ɶѻ���״ͼ
////        pm.makeStackedBarChart();
//        // �������ͼ
//        pm.makeLineAndShapeChart("lineAndShap_memory.png");
//    }
//
//    /**
//     * �������ͼ
//     */
//    public void makeLineAndShapeChart(String pictureName) {
//    	try {
//		    br = new BufferedReader(new FileReader(new File("D:\\meminfo.txt")));
//		    while ((line = br.readLine()) != null) {
//		    	list = line.split(",");
//		    	for (int i = 1; i < list.length; i++) {	
//		    		if (line.startsWith("nativesize")){
//		    			if (i == 1){//ֻ����һ��double����
//		    				nativeSizeArray = new double[list.length-1];  
//		    			}
//		    			//��list���1���±긳��nativeSizeArray��0���±꣬��Ϊlist��0����Ӣ�ĵ�
//		    			nativeSizeArray[i-1] = Double.parseDouble(list[i]);//ȥ��list�����еĵ�һ��Ӣ�ı�ʾ����ת��Ϊdouble����
//		    		} else if (line.startsWith("dalviksize")){
//		    			if (i == 1){
//		    				dalvikSizeArray = new double[list.length-1];  
//		    			}
//		    			dalvikSizeArray[i-1] = Double.parseDouble(list[i]);
//		    		} else if (line.startsWith("nativeallocated")){
//		    			if (i == 1){
//		    				nativeAllocatedArray = new double[list.length-1];  
//		    			}
//		    			nativeAllocatedArray[i-1] = Double.parseDouble(list[i]);
//		    		} else if (line.startsWith("dalvikallocated")){
//		    			if (i == 1){
//		    				dalvikAllocatedArray = new double[list.length-1];  
//		    			}
//		    			dalvikAllocatedArray[i-1] = Double.parseDouble(list[i]);
//		    		} else if (line.startsWith("nativePss")){
//		    			if (i == 1){
//		    				nativePSSArray = new double[list.length-1];  
//		    			}
//		    			nativePSSArray[i-1] = Double.parseDouble(list[i]);
//		    		} else if (line.startsWith("dalvikPss")){
//		    			if (i == 1){
//		    				dalvikPSSArray = new double[list.length-1];  
//		    			}
//		    			dalvikPSSArray[i-1] = Double.parseDouble(list[i]);
//		    		} else if (line.startsWith("totalPss")){
//		    			if (i == 1){
//		    				totalPSSArray = new double[list.length-1];  
//		    			}
//		    			totalPSSArray[i-1] = Double.parseDouble(list[i]);
//		    		} 
//				}
//		    };
//		    br.close();
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//    	double[][] data = new double[][]{//������data���
//    			nativeSizeArray,
//    			dalvikSizeArray,
//    			nativeAllocatedArray,
//    			dalvikAllocatedArray,
//    			nativePSSArray,
//    			dalvikPSSArray,
//    			totalPSSArray};
//        
//        String[] rowKeys = {//���м��������Ҫ���
//        		"nativeSize", 
//        		"dalvikSize", 
//        		"nativeAllocated", 
//        		"dalvikAllocated", 
//        		"nativePSS",
//        		"dalvikPSS",
//        		"totalPSS"};
//        //����x������
//        String[] columnKeys = new String[list.length-1];
//        for (int i = 0; i < list.length-1; i++) {
//        	columnKeys[i] = (i+1) + "s";
//		}
//        
//        CategoryDataset dataset = getBarData(data, rowKeys, columnKeys);
//        createTimeXYChar("Memory ����ͼ", "Time", "Memory Size(k)", dataset, pictureName);
//    }
//
//    /**
//     * ��ɷ������״ͼ
//     */
//    public void makeBarGroupChart() {
//        double[][] data = new double[][]{
//            {672, 766, 223, 540, 126},
//            {325, 521, 210, 340, 106},
//            {332, 256, 523, 240, 526}
//        };
//        String[] rowKeys = {"ƻ��", "����", "����"};
//        String[] columnKeys = {"����", "�Ϻ�", "����", "�ɶ�", "����"};
//        CategoryDataset dataset = getBarData(data, rowKeys, columnKeys);
//        createBarChart(dataset, "x���", "y���", "��״ͼ", "barGroup.png");
//    }
//
//    /**
//     * �����״ͼ
//     */
//    public void makeBarChart() {
//        double[][] data = new double[][]{
//            {672, 766, 223, 540, 126}
//        };
//        String[] rowKeys = {"ƻ��"};
//        String[] columnKeys = {"����", "�Ϻ�", "����", "�ɶ�", "����"};
//        CategoryDataset dataset = getBarData(data, rowKeys, columnKeys);
//        createBarChart(dataset, "x���", "y���", "��״ͼ", "bar.png");
//    }
//
//    /**
//     * ��ɶ�ջ��״ͼ
//     */
//    public void makeStackedBarChart() {
//        double[][] data = new double[][]{
//            {0.21, 0.66, 0.23, 0.40, 0.26},
//            {0.25, 0.21, 0.10, 0.40, 0.16}
//        };
//        String[] rowKeys = {"ƻ��", "����"};
//        String[] columnKeys = {"����", "�Ϻ�", "����", "�ɶ�", "����"};
//        CategoryDataset dataset = getBarData(data, rowKeys, columnKeys);
//        createStackedBarChart(dataset, "x���", "y���", "��״ͼ", "stsckedBar.png");
//    }
//
//    /**
//     * ��ɱ�״ͼ
//     */
//    public void makePieChart() {
//        double[] data = {9, 91};
//        String[] keys = {"ʧ����", "�ɹ���"};
//
//        createValidityComparePimChar(getDataPieSetByUtil(data, keys), "��״ͼ",
//                "pie2.png", keys);
//    }
//
//    // ��״ͼ,����ͼ ��ݼ�
//    public CategoryDataset getBarData(double[][] data, String[] rowKeys,
//            String[] columnKeys) {
//        return DatasetUtilities.createCategoryDataset(rowKeys, columnKeys, data);
//
//    }
//
//    // ��״ͼ ��ݼ�
//    public PieDataset getDataPieSetByUtil(double[] data,
//            String[] datadescription) {
//
//        if (data != null && datadescription != null) {
//            if (data.length == datadescription.length) {
//                DefaultPieDataset dataset = new DefaultPieDataset();
//                for (int i = 0; i < data.length; i++) {
//                    dataset.setValue(datadescription[i], data[i]);
//                }
//                return dataset;
//            }
//
//        }
//
//        return null;
//    }
//
//    /**
//     * ��״ͼ
//     * 
//     *@param dataset ��ݼ�
//     * @param xName x���˵�������࣬ʱ��ȣ�
//     * @param yName y���˵�����ٶȣ�ʱ��ȣ�
//     * @param chartTitle ͼ����
//     * @param charName ���ͼƬ������
//     * @return
//     */
//    public String createBarChart(CategoryDataset dataset, String xName,
//            String yName, String chartTitle, String charName) {
//        JFreeChart chart = ChartFactory.createBarChart(chartTitle, // ͼ�����
//                xName, // Ŀ¼�����ʾ��ǩ
//                yName, // ��ֵ�����ʾ��ǩ
//                dataset, // ��ݼ�
//                PlotOrientation.VERTICAL, // ͼ�?��ˮƽ����ֱ
//                true, // �Ƿ���ʾͼ��(���ڼ򵥵���״ͼ������false)
//                false, // �Ƿ���ɹ���
//                false // �Ƿ����URLt��
//                );
//        Font labelFont = new Font("SansSerif", Font.TRUETYPE_FONT, 12);
//        /*
//         * VALUE_TEXT_ANTIALIAS_OFF��ʾ�����ֵĿ���ݹر�,
//         * ʹ�õĹرտ���ݺ����御ѡ��12��14�ŵ�������,��������������ÿ�
//         */
//        // chart.getRenderingHints().put(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
//        chart.setTextAntiAlias(false);
//        chart.setBackgroundPaint(Color.white);
//        // create plot
//        CategoryPlot plot = chart.getCategoryPlot();
//        // ���ú����߿ɼ�
//        plot.setRangeGridlinesVisible(true);
//        // ����ɫ��
//        plot.setRangeGridlinePaint(Color.gray);
//
//        // ����ᾫ��
//        NumberAxis vn = (NumberAxis) plot.getRangeAxis();
//        // vn.setAutoRangeIncludesZero(true);
//        DecimalFormat df = new DecimalFormat("#0.00");
//        vn.setNumberFormatOverride(df); // �������ݱ�ǩ����ʾ��ʽ
//        // x������
//
//        CategoryAxis domainAxis = plot.getDomainAxis();
//        domainAxis.setLabelFont(labelFont);// �����
//
//        domainAxis.setTickLabelFont(labelFont);// ����ֵ
//
//        // Lable��Math.PI/3.0������б
//        // domainAxis.setCategoryLabelPositions(CategoryLabelPositions
//        // .createUpRotationLabelPositions(Math.PI / 3.0));
//
//        domainAxis.setMaximumCategoryLabelWidthRatio(0.6f);// �����ϵ� Lable �Ƿ�������ʾ
//
//        // ���þ���ͼƬ��˾���
//        domainAxis.setLowerMargin(0.1);
//        // ���þ���ͼƬ�Ҷ˾���
//        domainAxis.setUpperMargin(0.1);
//        // ���� columnKey �Ƿ�����ʾ
//        // domainAxis.setSkipCategoryLabelsToFit(true);
//
//        plot.setDomainAxis(domainAxis);
//        // ������ͼ����ɫ��ע�⣬ϵͳȡɫ��ʱ��Ҫʹ��16λ��ģʽ4�鿴��ɫ���룬����Ƚ�׼ȷ��
//        plot.setBackgroundPaint(new Color(255, 255, 204));
//
//        // y������
//        ValueAxis rangeAxis = plot.getRangeAxis();
//        rangeAxis.setLabelFont(labelFont);
//        rangeAxis.setTickLabelFont(labelFont);
//        // ������ߵ�һ�� Item ��ͼƬ���˵ľ���
//        rangeAxis.setUpperMargin(0.15);
//        // ������͵�һ�� Item ��ͼƬ�׶˵ľ���
//        rangeAxis.setLowerMargin(0.15);
//        plot.setRangeAxis(rangeAxis);
//
//        BarRenderer renderer = new BarRenderer();
//        // �������ӿ��
//        renderer.setMaximumBarWidth(0.05);
//        // �������Ӹ߶�
//        renderer.setMinimumBarLength(0.2);
//        // �������ӱ߿���ɫ
//        renderer.setBaseOutlinePaint(Color.BLACK);
//        // �������ӱ߿�ɼ�
//        renderer.setDrawBarOutline(true);
//
//        // // ���������ɫ
//        renderer.setSeriesPaint(0, new Color(204, 255, 255));
//        renderer.setSeriesPaint(1, new Color(153, 204, 255));
//        renderer.setSeriesPaint(2, new Color(51, 204, 204));
//
//        // ����ÿ���������ƽ�����֮�����
//        renderer.setItemMargin(0.0);
//
//        // ��ʾÿ�������ֵ�����޸ĸ���ֵ����������
//        renderer.setIncludeBaseInRange(true);
//        renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
//        renderer.setBaseItemLabelsVisible(true);
//
//        plot.setRenderer(renderer);
//        // �������͸���
//        plot.setForegroundAlpha(1.0f);
//
//        FileOutputStream fos_jpg = null;
//        try {
//            isChartPathExist(CHART_PATH);
//            String chartName = CHART_PATH + charName;
//            fos_jpg = new FileOutputStream(chartName);
//            ChartUtilities.writeChartAsPNG(fos_jpg, chart, 500, 500, true, 10);
//            return chartName;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        } finally {
//            try {
//                fos_jpg.close();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    /**
//     * ����ͼ
//     * 
//     * @param dataset ��ݼ�
//     * @param xName x���˵�������࣬ʱ��ȣ�
//     * @param yName y���˵�����ٶȣ�ʱ��ȣ�
//     * @param chartTitle ͼ����
//     * @param charName ���ͼƬ������
//     * @return
//     */
//    public String createHorizontalBarChart(CategoryDataset dataset,
//            String xName, String yName, String chartTitle, String charName) {
//        JFreeChart chart = ChartFactory.createBarChart(chartTitle, // ͼ�����
//                xName, // Ŀ¼�����ʾ��ǩ
//                yName, // ��ֵ�����ʾ��ǩ
//                dataset, // ��ݼ�
//                PlotOrientation.VERTICAL, // ͼ�?��ˮƽ����ֱ
//                true, // �Ƿ���ʾͼ��(���ڼ򵥵���״ͼ������false)
//                false, // �Ƿ���ɹ���
//                false // �Ƿ����URLt��
//                );
//
//        CategoryPlot plot = chart.getCategoryPlot();
//        // ����ᾫ��
//        NumberAxis vn = (NumberAxis) plot.getRangeAxis();
//        //���ÿ̶ȱ����0��ʼ
//        // vn.setAutoRangeIncludesZero(true);
//        DecimalFormat df = new DecimalFormat("#0.00");
//        vn.setNumberFormatOverride(df); // �������ݱ�ǩ����ʾ��ʽ
//
//        CategoryAxis domainAxis = plot.getDomainAxis();
//
//        domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45); // �����ϵ�
//        // Lable
//
//        Font labelFont = new Font("SansSerif", Font.TRUETYPE_FONT, 12);
//
//        domainAxis.setLabelFont(labelFont);// �����
//
//        domainAxis.setTickLabelFont(labelFont);// ����ֵ
//
//        domainAxis.setMaximumCategoryLabelWidthRatio(0.8f);// �����ϵ� Lable �Ƿ�������ʾ
//        // domainAxis.setVerticalCategoryLabels(false);
//
//        plot.setDomainAxis(domainAxis);
//
//        ValueAxis rangeAxis = plot.getRangeAxis();
//        // ������ߵ�һ�� Item ��ͼƬ���˵ľ���
//        rangeAxis.setUpperMargin(0.15);
//        // ������͵�һ�� Item ��ͼƬ�׶˵ľ���
//        rangeAxis.setLowerMargin(0.15);
//        plot.setRangeAxis(rangeAxis);
//        BarRenderer renderer = new BarRenderer();
//        // �������ӿ��
//        renderer.setMaximumBarWidth(0.03);
//        // �������Ӹ߶�
//        renderer.setMinimumBarLength(30);
//
//        renderer.setBaseOutlinePaint(Color.BLACK);
//
//        // ���������ɫ
//        renderer.setSeriesPaint(0, Color.GREEN);
//        renderer.setSeriesPaint(1, new Color(0, 0, 255));
//        // ����ÿ���������ƽ�����֮�����
//        renderer.setItemMargin(0.5);
//        // ��ʾÿ�������ֵ�����޸ĸ���ֵ����������
//        renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
//        // ���������ֵ�ɼ�
//        renderer.setBaseItemLabelsVisible(true);
//
//        plot.setRenderer(renderer);
//        // �������͸���
//        plot.setForegroundAlpha(0.6f);
//
//        FileOutputStream fos_jpg = null;
//        try {
//            isChartPathExist(CHART_PATH);
//            String chartName = CHART_PATH + charName;
//            fos_jpg = new FileOutputStream(chartName);
//            ChartUtilities.writeChartAsPNG(fos_jpg, chart, 500, 500, true, 10);
//            return chartName;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        } finally {
//            try {
//                fos_jpg.close();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    /**
//     * ��״ͼ
//     * 
//     * @param dataset ��ݼ�
//     * @param chartTitle ͼ����
//     * @param charName ���ͼ������
//     * @param pieKeys �ֱ�����ּ�
//     * @return
//     */
//    public String createValidityComparePimChar(PieDataset dataset,
//            String chartTitle, String charName, String[] pieKeys) {
//        JFreeChart chart = ChartFactory.createPieChart3D(chartTitle, // chart
//                // title
//                dataset,// data
//                true,// include legend
//                true, false);
//
//        // ʹ��˵���ǩ��������,ȥ���������
//        // chart.getRenderingHints().put(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);��Ч��
//        chart.setTextAntiAlias(false);
//        // ͼƬ����ɫ
//        chart.setBackgroundPaint(Color.white);
//        // ����ͼ�����������������title
//        Font font = new Font("e��", Font.BOLD, 25);
//        TextTitle title = new TextTitle(chartTitle);
//        title.setFont(font);
//        chart.setTitle(title);
//
//        PiePlot3D plot = (PiePlot3D) chart.getPlot();
//        // ͼƬ����ʾ�ٷֱ�:Ĭ�Ϸ�ʽ
//
//        // ָ����ͼ��*�ߵ���ɫ
//        // plot.setBaseSectionOutlinePaint(Color.BLACK);
//        // plot.setBaseSectionPaint(Color.BLACK);
//
//        // ���������ʱ����Ϣ
//        plot.setNoDataMessage("�޶�Ӧ����ݣ������²�ѯ��");
//
//        // ���������ʱ����Ϣ��ʾ��ɫ
//        plot.setNoDataMessagePaint(Color.red);
//
//        // ͼƬ����ʾ�ٷֱ�:�Զ��巽ʽ��{0} ��ʾѡ� {1} ��ʾ��ֵ�� {2} ��ʾ��ռ���� ,С����}λ
//        plot.setLabelGenerator(new StandardPieSectionLabelGenerator(
//                "{0}={1}({2})", NumberFormat.getNumberInstance(),
//                new DecimalFormat("0.00%")));
//        // ͼ����ʾ�ٷֱ�:�Զ��巽ʽ�� {0} ��ʾѡ� {1} ��ʾ��ֵ�� {2} ��ʾ��ռ����
//        plot.setLegendLabelGenerator(new StandardPieSectionLabelGenerator(
//                "{0}={1}({2})"));
//
//        plot.setLabelFont(new Font("SansSerif", Font.TRUETYPE_FONT, 12));
//
//        // ָ��ͼƬ��͸���(0.0-1.0)
//        plot.setForegroundAlpha(0.65f);
//        // ָ����ʾ�ı�ͼ��Բ��(false)����Բ��(true)
//        plot.setCircular(false, true);
//
//        // ���õ�һ�� ���section �Ŀ�ʼλ�ã�Ĭ����12���ӷ���
//        plot.setStartAngle(90);
//
//        // // ���÷ֱ���ɫ
//        plot.setSectionPaint(pieKeys[0], new Color(244, 194, 144));
//        plot.setSectionPaint(pieKeys[1], new Color(144, 233, 144));
//
//        FileOutputStream fos_jpg = null;
//        try {
//            // �ļ��в������򴴽�
//            isChartPathExist(CHART_PATH);
//            String chartName = CHART_PATH + charName;
//            fos_jpg = new FileOutputStream(chartName);
//            // �߿������Ӱ����Բ��ͼ����״
//            ChartUtilities.writeChartAsPNG(fos_jpg, chart, 500, 230);
//
//            return chartName;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        } finally {
//            try {
//                fos_jpg.close();
//                System.out.println("create pie-chart.");
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//
//    }
//
//    /**
//     * �ж��ļ����Ƿ���ڣ����������½�
//     * @param chartPath
//     */
//    private void isChartPathExist(String chartPath) {
//        File file = new File(chartPath);
//        if (!file.exists()) {
//            file.mkdirs();
//        // log.info("CHART_PATH="+CHART_PATH+"create.");
//        }
//    }
//
//    /**
//     * ����ͼ
//     * 
//     * @param chartTitle
//     * @param x
//     * @param y
//     * @param xyDataset
//     * @param charName
//     * @return
//     */
//    public String createTimeXYChar(String chartTitle, String x, String y,
//            CategoryDataset xyDataset, String charName) {
//
//        JFreeChart chart = ChartFactory.createLineChart(chartTitle, x, y,
//                xyDataset, PlotOrientation.VERTICAL, true, true, false);
//
//        chart.setTextAntiAlias(false);
//        chart.setBackgroundPaint(Color.WHITE);
//        // ����ͼ�����������������title
//        Font font = new Font("e��", Font.BOLD, 25);
//        TextTitle title = new TextTitle(chartTitle);
//        title.setFont(font);
//        chart.setTitle(title);
//        // �����������
//        Font labelFont = new Font("SansSerif", Font.TRUETYPE_FONT, 12);
//
//        chart.setBackgroundPaint(Color.WHITE);
//
//        CategoryPlot categoryplot = (CategoryPlot) chart.getPlot();
//        // x�� // ����������Ƿ�ɼ�
//        categoryplot.setDomainGridlinesVisible(true);
//        // y�� //���������Ƿ�ɼ�
//        categoryplot.setRangeGridlinesVisible(true);
//
//        categoryplot.setRangeGridlinePaint(Color.WHITE);// ����ɫ��
//
//        categoryplot.setDomainGridlinePaint(Color.WHITE);// ����ɫ��
//
//        categoryplot.setBackgroundPaint(Color.lightGray);
//
//        // ����������֮��ľ���
//        // categoryplot.setAxisOffset(new RectangleInsets(5D, 5D, 5D, 5D));
//
//        CategoryAxis domainAxis = categoryplot.getDomainAxis();
//
//        domainAxis.setLabelFont(labelFont);// �����
//
//        domainAxis.setTickLabelFont(labelFont);// ����ֵ
//
//        domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45); // �����ϵ�
//        // Lable
//        // 45����б
//        // ���þ���ͼƬ��˾���
//
//        domainAxis.setLowerMargin(0.0);
//        // ���þ���ͼƬ�Ҷ˾���
//        domainAxis.setUpperMargin(0.0);
//
//        NumberAxis numberaxis = (NumberAxis) categoryplot.getRangeAxis();
//        numberaxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
//        numberaxis.setAutoRangeIncludesZero(true);
//
//        // ���renderer ע���������������͵�lineandshaperenderer����
//        LineAndShapeRenderer lineandshaperenderer = (LineAndShapeRenderer) categoryplot.getRenderer();
//
//        lineandshaperenderer.setBaseShapesVisible(true); // series �㣨����ݵ㣩�ɼ�
//
//        lineandshaperenderer.setBaseLinesVisible(true); // series �㣨����ݵ㣩����l�߿ɼ�
//
//        // ��ʾ�۵����
//        // lineandshaperenderer.setBaseItemLabelGenerator(new
//        // StandardCategoryItemLabelGenerator());
//        // lineandshaperenderer.setBaseItemLabelsVisible(true);
//
//        FileOutputStream fos_jpg = null;
//        try {
//            isChartPathExist(CHART_PATH);
//            String chartName = CHART_PATH + charName;
//            fos_jpg = new FileOutputStream(chartName);
//
//            // �����?��Ϊpng�ļ�
//            ChartUtilities.writeChartAsPNG(fos_jpg, chart, 500, 510);
//
//            return chartName;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        } finally {
//            try {
//                fos_jpg.close();
//                System.out.println("create time-createTimeXYChar.");
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    /**
//     * ��ջ��״ͼ
//     * 
//     * @param dataset
//     * @param xName
//     * @param yName
//     * @param chartTitle
//     * @param charName
//     * @return
//     */
//    public String createStackedBarChart(CategoryDataset dataset, String xName,
//            String yName, String chartTitle, String charName) {
//        // 1:�õ� CategoryDataset
//
//        // 2:JFreeChart����
//        JFreeChart chart = ChartFactory.createStackedBarChart(chartTitle, // ͼ�����
//                xName, // Ŀ¼�����ʾ��ǩ
//                yName, // ��ֵ�����ʾ��ǩ
//                dataset, // ��ݼ�
//                PlotOrientation.VERTICAL, // ͼ�?��ˮƽ����ֱ
//                true, // �Ƿ���ʾͼ��(���ڼ򵥵���״ͼ������false)
//                false, // �Ƿ���ɹ���
//                false // �Ƿ����URLt��
//                );
//        // ͼ����������
//        chart.setTextAntiAlias(false);
//
//        chart.setBackgroundPaint(Color.WHITE);
//
//        // 2 ��2 �������� ���������� TextTitle ����
//        chart.setTitle(new TextTitle(chartTitle, new Font("e��", Font.BOLD,
//                25)));
//        // 2 ��2.1:��������
//        // x,y���������
//        Font labelFont = new Font("SansSerif", Font.TRUETYPE_FONT, 12);
//
//        // 2 ��3 Plot ���� Plot ������ͼ�εĻ��ƽṹ����
//        CategoryPlot plot = chart.getCategoryPlot();
//
//        // ���ú����߿ɼ�
//        plot.setRangeGridlinesVisible(true);
//        // ����ɫ��
//        plot.setRangeGridlinePaint(Color.gray);
//
//        // ����ᾫ��
//        NumberAxis vn = (NumberAxis) plot.getRangeAxis();
//        // �������ֵ��1
//        vn.setUpperBound(1);
//        // �������������0��ʼ
//        // vn.setAutoRangeIncludesZero(true);
//        // �����ʾ��ʽ�ǰٷֱ�
//        DecimalFormat df = new DecimalFormat("0.00%");
//        vn.setNumberFormatOverride(df); // �������ݱ�ǩ����ʾ��ʽ
//        // DomainAxis �������ᣬ�൱�� x �ᣩ�� RangeAxis ����Χ�ᣬ�൱�� y �ᣩ
//
//        CategoryAxis domainAxis = plot.getDomainAxis();
//
//        domainAxis.setLabelFont(labelFont);// �����
//
//        domainAxis.setTickLabelFont(labelFont);// ����ֵ
//
//        // x�����̫��������������б������}�ַ�ʽѡ��һ��}��Ч����ͬ
//        // ��б��1�������ϵ� Lable 45����б
//        // domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);
//        // ��б��2��Lable��Math.PI 3.0������б
//        // domainAxis.setCategoryLabelPositions(CategoryLabelPositions
//        // .createUpRotationLabelPositions(Math.PI / 3.0));
//
//        domainAxis.setMaximumCategoryLabelWidthRatio(0.6f);// �����ϵ� Lable �Ƿ�������ʾ
//
//        plot.setDomainAxis(domainAxis);
//
//        // y������
//        ValueAxis rangeAxis = plot.getRangeAxis();
//        rangeAxis.setLabelFont(labelFont);
//        rangeAxis.setTickLabelFont(labelFont);
//        // ������ߵ�һ�� Item ��ͼƬ���˵ľ���
//        rangeAxis.setUpperMargin(0.15);
//        // ������͵�һ�� Item ��ͼƬ�׶˵ľ���
//        rangeAxis.setLowerMargin(0.15);
//        plot.setRangeAxis(rangeAxis);
//
//        // Renderer ������ͼ�εĻ��Ƶ�Ԫ
//        StackedBarRenderer renderer = new StackedBarRenderer();
//        // �������ӿ��
//        renderer.setMaximumBarWidth(0.05);
//        // �������Ӹ߶�
//        renderer.setMinimumBarLength(0.1);
//        //������ı߿���ɫ
//        renderer.setBaseOutlinePaint(Color.BLACK);
//        //������ı߿�ɼ�
//        renderer.setDrawBarOutline(true);
//
//        // // ���������ɫ(���趨Ҳ��Ĭ��)
//        renderer.setSeriesPaint(0, new Color(204, 255, 204));
//        renderer.setSeriesPaint(1, new Color(255, 204, 153));
//
//        // ����ÿ���������ƽ�����֮�����
//        renderer.setItemMargin(0.4);
//
//        plot.setRenderer(renderer);
//        // �������͸���(�����3D�ı������ò��ܴﵽb��Ч�������2D��������ʹ��ɫ�䵭)
//        // plot.setForegroundAlpha(0.65f);
//
//        FileOutputStream fos_jpg = null;
//        try {
//            isChartPathExist(CHART_PATH);
//            String chartName = CHART_PATH + charName;
//            fos_jpg = new FileOutputStream(chartName);
//            ChartUtilities.writeChartAsPNG(fos_jpg, chart, 500, 500, true, 10);
//            return chartName;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        } finally {
//            try {
//                fos_jpg.close();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }
//}
//
