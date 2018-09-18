public class SetupTest{

    @Test
    public void exampleTest(){
        // Arrange
        final int expected = 4;

        // Act
        final int actual = Math.add(2, 2);

        // Assert
        Assert.assertEquals(actual, expected);
    }
}