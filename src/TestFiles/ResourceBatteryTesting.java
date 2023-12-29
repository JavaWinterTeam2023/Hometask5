package TestFiles;
import Services.*;

import static org.junit.jupiter.api.Assertions.*;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ResourceBatteryTesting {
	private ResourceBuffer resourceBuffer;
    private final int bufferSize = 5;

    @BeforeEach
    void setUp() {
        resourceBuffer = new ResourceBuffer(bufferSize);
    }

    @Test
    void add_ShouldAddBatteryToBuffer() throws InterruptedException {
        resourceBuffer.add(1);
        assertEquals(1, resourceBuffer.getQueueSize());
    }

    @Test
    void free_ShouldRemoveBatteryFromBuffer() throws InterruptedException {
        resourceBuffer.add(1);
        resourceBuffer.free("Solar");
        assertEquals(0, resourceBuffer.getQueueSize());
    }

    @Test
    void add_ShouldBlockWhenBufferIsFull() throws InterruptedException {
        // Fill up the buffer
        for (int i = 1; i <= bufferSize; i++) {
            resourceBuffer.add(i);
        }

        // Use a separate thread to add a battery to the buffer
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(() -> {
            resourceBuffer.add(bufferSize + 1);
        });

        // Wait for some time to ensure the add operation is blocked
        Thread.sleep(1000);

        // The buffer size should still be equal to the maximum size
        assertEquals(bufferSize, resourceBuffer.getQueueSize());

        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.SECONDS);
    }

    @Test
    void free_ShouldBlockWhenBufferIsEmpty() throws InterruptedException {
        // Use a separate thread to free a battery from the buffer
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(() -> {
            resourceBuffer.free("Solar");
        });

        // Wait for some time to ensure the free operation is blocked
        Thread.sleep(1000);

        // The buffer size should still be empty (0)
        assertEquals(0, resourceBuffer.getQueueSize());

        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.SECONDS);
    }
}
