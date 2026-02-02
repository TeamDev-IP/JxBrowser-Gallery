/*
 * Copyright 2026, TeamDev
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.teamdev.jxbrowser.angular.backend;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.teamdev.jxbrowser.js.JsAccessible;

import java.util.Arrays;
import java.util.List;

/**
 * Java backend service that provides data for the Angular Dashboard.
 */
@JsAccessible
public class DashboardBackend {

    private final Gson gson = new GsonBuilder().create();

    /**
     * Returns the top statistics cards data as JSON.
     */
    public String getTopCards() {
        List<TopCard> cards = Arrays.asList(
                new TopCard("success", "bi bi-wallet", "$21,456", "Total Revenue",
                        true, "+12.5%", "Trending up this month"),
                new TopCard("danger", "bi bi-coin", "$1,250", "Refund Given",
                        false, "-20%", "Down 20% this period"),
                new TopCard("warning", "bi bi-basket3", "456", "Total Projects",
                        true, "+12.5%", "Strong user retention"),
                new TopCard("info", "bi bi-bag", "210", "Weekly Sales",
                        true, "+4.5%", "Steady performance increase")
        );
        return gson.toJson(cards);
    }

    /**
     * Returns the activity feeds data as JSON.
     */
    public String getFeeds() {
        List<Feed> feeds = Arrays.asList(
                new Feed("bg-info", "bi bi-bell", "You have 4 pending tasks.", "Just Now"),
                new Feed("bg-success", "bi bi-hdd", "Server #1 overloaded.", "2 Hours ago"),
                new Feed("bg-warning", "bi bi-bag-check", "New order received.", "31 May"),
                new Feed("bg-danger", "bi bi-person", "New user registered.", "30 May"),
                new Feed("bg-primary", "bi bi-shield-check", "System update completed.", "29 May"),
                new Feed("bg-info", "bi bi-bell", "Payment processed successfully.", "28 May")
        );
        return gson.toJson(feeds);
    }

    /**
     * Returns the projects table data as JSON.
     */
    public String getProjects() {
        List<Project> projects = Arrays.asList(
                new Project("assets/images/user1.jpg", "John Smith", "john@example.com",
                        "JxBrowser Demo", "success", 12, "$15K"),
                new Project("assets/images/user2.jpg", "Sarah Wilson", "sarah@example.com",
                        "Angular Dashboard", "info", 8, "$12K"),
                new Project("assets/images/user3.jpg", "Mike Johnson", "mike@example.com",
                        "Java Backend", "warning", 15, "$20K"),
                new Project("assets/images/user4.jpg", "Emily Brown", "emily@example.com",
                        "Desktop App", "danger", 6, "$8K")
        );
        return gson.toJson(projects);
    }

    /**
     * Returns the sales summary data as JSON.
     */
    public String getSalesSummary() {
        SalesSummary summary = new SalesSummary("$10,345", "$7,545", "$1,345");
        return gson.toJson(summary);
    }

    /**
     * Returns the chart series data as JSON.
     */
    public String getChartData(String timeRange) {
        List<ChartSeries> series = switch (timeRange) {
            case "7d" -> Arrays.asList(
                    new ChartSeries("Desktop", new int[]{186, 305, 237, 173, 209, 214, 186}),
                    new ChartSeries("Mobile", new int[]{80, 120, 95, 85, 110, 98, 76})
            );
            case "30d" -> Arrays.asList(
                    new ChartSeries("Desktop", new int[]{1250, 1380, 1520, 1420}),
                    new ChartSeries("Mobile", new int[]{620, 710, 680, 750})
            );
            default -> Arrays.asList(
                    new ChartSeries("Desktop", new int[]{31, 40, 28, 51, 42, 109, 100}),
                    new ChartSeries("Mobile", new int[]{11, 32, 45, 32, 34, 52, 41})
            );
        };
        return gson.toJson(series);
    }

    private record TopCard(String bgcolor, String icon, String title, String subtitle,
                              boolean trendDirection, String trendValue, String trendDescription) {}

    private record Feed(String bgClass, String icon, String task, String time) {}

    private record Project(String image, String name, String email, String project,
                           String status, int weeks, String budget) {}

    private record SalesSummary(String totalSales, String thisMonth, String thisWeek) {}

    private record ChartSeries(String name, int[] data) {}
}
