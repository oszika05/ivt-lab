package hu.bme.mit.spaceship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class GT4500Test {

  private GT4500 ship;
  private TorpedoStore primaryStoreMock;
  private TorpedoStore secondaryStoreMock;

  @BeforeEach
  public void init() {
    primaryStoreMock = mock(TorpedoStore.class);
    secondaryStoreMock = mock(TorpedoStore.class);
    this.ship = new GT4500(primaryStoreMock, secondaryStoreMock);
  }

  @Test
  public void fireTorpedo_Single_Success() {
    // Arrange
    when(primaryStoreMock.isEmpty()).thenReturn(false);
    when(primaryStoreMock.fire(1)).thenReturn(true);
    when(secondaryStoreMock.isEmpty()).thenReturn(false);
    when(secondaryStoreMock.fire(1)).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(true, result);
  }

  @Test
  public void fireTorpedo_All_Success() {
    // Arrange
    when(primaryStoreMock.isEmpty()).thenReturn(false);
    when(primaryStoreMock.fire(1)).thenReturn(true);
    when(secondaryStoreMock.isEmpty()).thenReturn(false);
    when(secondaryStoreMock.fire(1)).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    assertEquals(true, result);
  }
  @Test
  public void fireTorpedo_BothFires() {
    // Arrange
    when(primaryStoreMock.isEmpty()).thenReturn(false);
    when(primaryStoreMock.fire(1)).thenReturn(true);
    when(secondaryStoreMock.isEmpty()).thenReturn(false);
    when(secondaryStoreMock.fire(1)).thenReturn(true);

    // Act
    ship.fireTorpedo(FiringMode.ALL);

    // Assert
    verify(primaryStoreMock, times(1)).fire(anyInt());
    verify(secondaryStoreMock, times(1)).fire(anyInt());
  }

  @Test
  public void fireTorpedo_FirstIsPrimary() {
    // Arrange
    when(primaryStoreMock.isEmpty()).thenReturn(false);
    when(primaryStoreMock.fire(1)).thenReturn(true);
    when(secondaryStoreMock.isEmpty()).thenReturn(false);
    when(secondaryStoreMock.fire(1)).thenReturn(true);

    // Act
    ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    verify(primaryStoreMock, times(1)).fire(anyInt());
    verify(secondaryStoreMock, never()).fire(anyInt());
  }

  @Test
  public void fireTorpedo_SecondIsSecondary() {
    // Arrange
    when(primaryStoreMock.isEmpty()).thenReturn(false);
    when(primaryStoreMock.fire(1)).thenReturn(true);
    when(secondaryStoreMock.isEmpty()).thenReturn(false);
    when(secondaryStoreMock.fire(1)).thenReturn(true);

    // Act
    ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    verify(primaryStoreMock, times(1)).fire(anyInt());
    verify(secondaryStoreMock, never()).fire(anyInt());

    // Act
    ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    verify(primaryStoreMock, times(1)).fire(anyInt());
    verify(secondaryStoreMock, times(1)).fire(anyInt());
  }

  @Test
  public void fireTorpedo_When_PrimaryIsEmpty_StartWithSecondary() {
    // Arrange
    when(primaryStoreMock.isEmpty()).thenReturn(true);
    when(primaryStoreMock.fire(1)).thenReturn(true);
    when(secondaryStoreMock.isEmpty()).thenReturn(false);
    when(secondaryStoreMock.fire(1)).thenReturn(true);

    // Act
    ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    verify(primaryStoreMock, never()).fire(anyInt());
    verify(secondaryStoreMock, times(1)).fire(anyInt());
  }

  @Test
  public void fireTorpedo_When_PrimaryFails_DoNotFireSecondary() {
    // Arrange
    when(primaryStoreMock.isEmpty()).thenReturn(false);
    when(primaryStoreMock.fire(1)).thenReturn(false);
    when(secondaryStoreMock.isEmpty()).thenReturn(false);
    when(secondaryStoreMock.fire(1)).thenReturn(true);

    // Act
    ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    verify(primaryStoreMock, times(1)).fire(anyInt());
    verify(secondaryStoreMock, never()).fire(anyInt());
  }


  // fire in all mode: both torpedo fires
  // first time primary is fired
  // second time secondary is fired
  // if the primary is empty, it starts with secondary
  // if one fails, nothing is fired (not even the second)

}
