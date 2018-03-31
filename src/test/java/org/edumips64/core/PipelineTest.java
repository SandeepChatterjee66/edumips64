package org.edumips64.core;

import org.edumips64.BaseWithInstructionBuilderTest;
import org.edumips64.core.is.BUBBLE;
import org.junit.Test;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class PipelineTest extends BaseWithInstructionBuilderTest {
  private Pipeline pipeline = new Pipeline();
  
  @Before
  public void testSetup() {
    super.testSetup();
  }

  @Test
  public void testEmptyPipeline() {
    assertEquals(0, pipeline.size());
    assertTrue(pipeline.isEmpty(CPU.PipeStage.IF));
    assertTrue(pipeline.isEmpty(CPU.PipeStage.ID));
    assertTrue(pipeline.isEmpty(CPU.PipeStage.EX));
    assertTrue(pipeline.isEmpty(CPU.PipeStage.MEM));
    assertTrue(pipeline.isEmpty(CPU.PipeStage.WB));
    
    // Test that even with an empty pipeline the getters work.
    assertEquals(null, pipeline.IF());
    assertEquals(null, pipeline.ID());
    assertEquals(null, pipeline.EX());
    assertEquals(null, pipeline.MEM());
    assertEquals(null, pipeline.WB());
  }
  
  @Test
  public void testNonEmptySize() {
    assertEquals(0, pipeline.size());
    pipeline.setIF(instructionBuilder.buildInstruction("ADD"));
    pipeline.setID(instructionBuilder.buildInstruction("ADD"));
    assertEquals(2, pipeline.size());
  }

  @Test
  public void testSizeIncreaseWithBubble() {
    assertEquals(0, pipeline.size());
    pipeline.setIF(new BUBBLE());
    assertEquals(1, pipeline.size());
  }
  
  @Test
  public void testSizeIncreaseWithOtherInstructions() {
    assertEquals(0, pipeline.size());
    pipeline.setIF(instructionBuilder.buildInstruction("ADD"));
    assertEquals(1, pipeline.size());
  }

  @Test
  public void testIsBubble() {
    pipeline.setIF(new BUBBLE());
    assertTrue(pipeline.isBubble(CPU.PipeStage.IF));
    assertTrue(pipeline.isEmptyOrBubble(CPU.PipeStage.IF));
    
    pipeline.setIF(instructionBuilder.buildInstruction("ADD"));
    assertFalse(pipeline.isBubble(CPU.PipeStage.IF));
    assertFalse(pipeline.isEmptyOrBubble(CPU.PipeStage.IF));
  }
  
  @Test
  public void testClear() {
    assertEquals(0, pipeline.size());
    pipeline.setIF(new BUBBLE());
    pipeline.setID(new BUBBLE());
    pipeline.setEX(new BUBBLE());
    pipeline.setMEM(new BUBBLE());
    pipeline.setWB(new BUBBLE());
    assertEquals(5, pipeline.size());
    
    pipeline.clear();
    assertEquals(0, pipeline.size());
    assertEquals(null, pipeline.IF());
    assertEquals(null, pipeline.ID());
    assertEquals(null, pipeline.EX());
    assertEquals(null, pipeline.MEM());
    assertEquals(null, pipeline.WB());
  }
}