package com.example.fiszingsymulatjon;

import javafx.application.Platform;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class HelloControllerTest {
    
    @Test
    void testPodstawowychOperacji() {
        // Ten test należy uruchomić w środowisku JavaFX
        Platform.startup(() -> {
            HelloController controller = new HelloController();
            // Tutaj testy specyficzne dla kontrolera
        });
    }
}