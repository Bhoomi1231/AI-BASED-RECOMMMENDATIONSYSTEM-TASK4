import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;

import java.io.File;
import java.util.List;

public class RecommenderApp {
    public static void main(String[] args) {
        try {
            // Load CSV data model (userId, itemId, rating)
            File ratingsFile = new File("data.csv");
            DataModel model = new FileDataModel(ratingsFile);

            // Similarity metric: Pearson correlation
            UserSimilarity similarity = new PearsonCorrelationSimilarity(model);

            // Find 2 nearest users
            UserNeighborhood neighborhood = new NearestNUserNeighborhood(2, similarity, model);

            // Build the recommender
            Recommender recommender = new GenericUserBasedRecommender(model, neighborhood, similarity);

            // Recommend top 2 items for user 1
            List<RecommendedItem> recommendations = recommender.recommend(1, 2);

            // Output recommendations
            System.out.println("Recommendations for User 1:");
            for (RecommendedItem item : recommendations) {
                System.out.printf("Item ID: %d | Estimated Rating: %.2f\n", item.getItemID(), item.getValue());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}