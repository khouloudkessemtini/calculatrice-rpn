package com.example.calculatrice.service;

import com.example.calculatrice.IllegalBusinessLogiqueException;
import com.example.calculatrice.domain.StackCalculator;
import com.example.calculatrice.domain.StackElement;
import com.example.calculatrice.domain.StackElementPK;
import com.example.calculatrice.dto.Operator;
import com.example.calculatrice.repository.StackElementRepository;
import com.example.calculatrice.repository.StackRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;
import static org.mockito.BDDMockito.given;

public class StackServiceTest {

    private StackService stackService;

    @Mock
    private StackRepository stackRepository;

    @Mock
    private StackElementRepository stackElementRepository;

    StackCalculator stackCalculator = null;
    List<StackElement> stackElementsLastTwo = new ArrayList<>();

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
        this.stackService = new StackService(stackRepository, stackElementRepository);

        Integer id = 1;
        stackCalculator = new StackCalculator(id, "test");

        StackElement detail1 = new StackElement();
        detail1.setValue(5);
        detail1.setStackElementPK(new StackElementPK(id, 1));
        stackElementsLastTwo.add(detail1);

        StackElement detail2 = new StackElement();
        detail2.setValue(6);
        detail2.setStackElementPK(new StackElementPK(id, 2));
        stackElementsLastTwo.add(detail2);
    }

    @Test
    public void testApplyOperatorToStackWhenAddOperator() {


        Operator operator = Operator.ADD;

        given(stackRepository.findById(stackCalculator.getCode())).willReturn(Optional.of(stackCalculator));
        given(stackElementRepository.findTop2ByStackElementPK_CodeStackCalculatorOrderByStackElementPK_OrderValueDesc(stackCalculator.getCode())).willReturn(stackElementsLastTwo);

        ArgumentCaptor<StackElement> stackCalculatorDetailCapture = ArgumentCaptor.forClass(StackElement.class);
        StackElement stackCalculatorDetailMock = new StackElement();
        given(stackElementRepository.save(stackCalculatorDetailCapture.capture())).willReturn(stackCalculatorDetailMock);

        stackService.applyOperatorToStack(stackCalculator.getCode(), operator);
        assertThat(stackCalculatorDetailCapture.getValue()).extracting(StackElement::getValue).isEqualTo(11);
    }

    @Test
    public void testApplyOperatorToStackWhenSubtractOperator() {

        Operator operator = Operator.SUBTRACT;

        given(stackRepository.findById(stackCalculator.getCode())).willReturn(Optional.of(stackCalculator));
        given(stackElementRepository.findTop2ByStackElementPK_CodeStackCalculatorOrderByStackElementPK_OrderValueDesc(stackCalculator.getCode())).willReturn(stackElementsLastTwo);

        ArgumentCaptor<StackElement> stackCalculatorDetailCapture = ArgumentCaptor.forClass(StackElement.class);
        StackElement stackCalculatorDetailMock = new StackElement();
        given(stackElementRepository.save(stackCalculatorDetailCapture.capture())).willReturn(stackCalculatorDetailMock);

        stackService.applyOperatorToStack(stackCalculator.getCode(), operator);
        assertThat(stackCalculatorDetailCapture.getValue()).extracting(StackElement::getValue).isEqualTo(-1);
    }


    @Test
    public void testApplyOperatorToStackWhenMultiplyOperator() {

        Operator operator = Operator.MULTIPLY;

        given(stackRepository.findById(stackCalculator.getCode())).willReturn(Optional.of(stackCalculator));
        given(stackElementRepository.findTop2ByStackElementPK_CodeStackCalculatorOrderByStackElementPK_OrderValueDesc(stackCalculator.getCode())).willReturn(stackElementsLastTwo);

        ArgumentCaptor<StackElement> stackCalculatorDetailCapture = ArgumentCaptor.forClass(StackElement.class);
        StackElement stackCalculatorDetailMock = new StackElement();
        given(stackElementRepository.save(stackCalculatorDetailCapture.capture())).willReturn(stackCalculatorDetailMock);

        stackService.applyOperatorToStack(stackCalculator.getCode(), operator);
        assertThat(stackCalculatorDetailCapture.getValue()).extracting(StackElement::getValue).isEqualTo(30);
    }

    @Test
    public void testApplyOperatorToStackWhenDiviseOperatorShouldReturnValue() {

        Operator operator = Operator.DIVIDE;

        List<StackElement> stackElementsLastTwo = new ArrayList<>();
        StackElement detail1 = new StackElement();
        detail1.setValue(10);
        detail1.setStackElementPK(new StackElementPK(stackCalculator.getCode(), 1));
        stackElementsLastTwo.add(detail1);

        StackElement detail2 = new StackElement();
        detail2.setValue(5);
        detail2.setStackElementPK(new StackElementPK(stackCalculator.getCode(), 2));
        stackElementsLastTwo.add(detail2);

        given(stackRepository.findById(stackCalculator.getCode())).willReturn(Optional.of(stackCalculator));
        given(stackElementRepository.findTop2ByStackElementPK_CodeStackCalculatorOrderByStackElementPK_OrderValueDesc(stackCalculator.getCode())).willReturn(stackElementsLastTwo);

        ArgumentCaptor<StackElement> stackCalculatorDetailCapture = ArgumentCaptor.forClass(StackElement.class);
        StackElement stackCalculatorDetailMock = new StackElement();
        given(stackElementRepository.save(stackCalculatorDetailCapture.capture())).willReturn(stackCalculatorDetailMock);

            stackService.applyOperatorToStack(stackCalculator.getCode(), operator);
        assertThat(stackCalculatorDetailCapture.getValue()).extracting(StackElement::getValue).isEqualTo(2);
    }


    @Test
    public void testApplyOperatorToStackWhenDiviseOperatorShouldThrowException() {

        Operator operator = Operator.DIVIDE;

        List<StackElement> stackElementsLastTwo = new ArrayList<>();
        StackElement detail1 = new StackElement();
        detail1.setValue(5);
        detail1.setStackElementPK(new StackElementPK(stackCalculator.getCode(), 1));
        stackElementsLastTwo.add(detail1);

        StackElement detail2 = new StackElement();
        detail2.setValue(0);
        detail2.setStackElementPK(new StackElementPK(stackCalculator.getCode(), 2));
        stackElementsLastTwo.add(detail2);

        given(stackRepository.findById(stackCalculator.getCode())).willReturn(Optional.of(stackCalculator));
        given(stackElementRepository.findTop2ByStackElementPK_CodeStackCalculatorOrderByStackElementPK_OrderValueDesc(stackCalculator.getCode())).willReturn(stackElementsLastTwo);

        ArgumentCaptor<StackElement> stackCalculatorDetailCapture = ArgumentCaptor.forClass(StackElement.class);
        StackElement stackCalculatorDetailMock = new StackElement();
        given(stackElementRepository.save(stackCalculatorDetailCapture.capture())).willReturn(stackCalculatorDetailMock);

        assertThrows(IllegalBusinessLogiqueException.class, () -> {
            stackService.applyOperatorToStack(stackCalculator.getCode(), operator);
        });
    }
}
