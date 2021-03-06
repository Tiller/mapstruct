/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.selection.twosteperror;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.ap.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.ap.testutil.compilation.annotation.ExpectedCompilationOutcome;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

@RunWith( AnnotationProcessorTestRunner.class )
public class TwoStepMappingTest {

    @Test
    @WithClasses( ErroneousMapperMM.class )
    @ExpectedCompilationOutcome( value = CompilationResult.FAILED,
        diagnostics = @Diagnostic(
            kind = javax.tools.Diagnostic.Kind.ERROR,
            type = ErroneousMapperMM.class,
            line = 16,
            message = "Ambiguous 2step methods found, mapping SourceType s to TargetType. "
                + "Found methodY( methodX ( parameter ) ): "
                + "method(s)Y: TargetType:methodY1(TypeInTheMiddleA), methodX: TypeInTheMiddleA:methodX1(SourceType); "
                + "method(s)Y: TargetType:methodY2(TypeInTheMiddleB), methodX: TypeInTheMiddleB:methodX2(SourceType); ."
        ) )
    public void methodAndMethodTest() {
    }

    @Test
    @WithClasses( ErroneousMapperCM.class )
    @ExpectedCompilationOutcome( value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(
                kind = javax.tools.Diagnostic.Kind.ERROR,
                type = ErroneousMapperCM.class,
                line = 18,
                message = "Ambiguous 2step methods found, mapping BigDecimal s to TargetType. "
                    + "Found methodY( conversionX ( parameter ) ): "
                    + "method(s)Y: TargetType:methodY1(String), conversionX: BigDecimal-->String; "
                    + "method(s)Y: TargetType:methodY2(Double), conversionX: BigDecimal-->Double; ."
            ),
            @Diagnostic(
                kind = javax.tools.Diagnostic.Kind.ERROR,
                type = ErroneousMapperCM.class,
                line = 18,
                messageRegExp = "Can't map property.*"
            )
        } )
    public void conversionAndMethodTest() {
    }

    @Test
    @WithClasses( ErroneousMapperMC.class )
    @ExpectedCompilationOutcome( value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(
                kind = javax.tools.Diagnostic.Kind.ERROR,
                type = ErroneousMapperMC.class,
                line = 18,
                message = "Ambiguous 2step methods found, mapping SourceType s to String. "
                    + "Found conversionY( methodX ( parameter ) ): "
                    + "conversionY: BigDecimal-->String, method(s)X: BigDecimal:methodX1(SourceType); "
                    + "conversionY: Double-->String, method(s)X: Double:methodX2(SourceType); ."
            ),
            @Diagnostic(
                kind = javax.tools.Diagnostic.Kind.ERROR,
                type = ErroneousMapperMC.class,
                line = 18,
                messageRegExp = "Can't map property.*"
            )
        } )
    public void methodAndConversionTest() {
    }
}
