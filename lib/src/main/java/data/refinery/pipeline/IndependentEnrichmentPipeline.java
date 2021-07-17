//package data.refinery.pipeline;
//
//import data.refinery.schema.EntityFieldReadAccessor;
//import data.refinery.schema.EntityFieldReadWriteAccessor;
//import data.refinery.schema.EntitySchema;
//
//import java.util.concurrent.CompletableFuture;
//import java.util.concurrent.Executor;
//import java.util.function.Supplier;
//
//import static com.google.common.base.Preconditions.checkNotNull;
//
//final class IndependentEnrichmentPipeline implements PipelineEngine {
//
//    private final EnrichmentList enrichments;
//    private final CalculationFactory calculationFactory;
//    private final Supplier<EntityFieldReadWriteAccessor> outputFactory;
//    private final Executor executor;
//
//    private final EntitySchema fixedSchema;
//
//    IndependentEnrichmentPipeline(EntitySchema inputSchema,
//                                  EnrichmentList enrichments,
//                                  CalculationFactory calculationFactory,
//                                  Supplier<EntityFieldReadWriteAccessor> outputFactory,
//                                  Executor executor) {
//        this.enrichments = enrichments;
//        this.calculationFactory = checkNotNull(calculationFactory);
//        this.outputFactory = checkNotNull(outputFactory);
//        this.executor = checkNotNull(executor);
//        this.fixedSchema = PipelineEngineHelpers.determineFixedSchema(inputSchema, enrichments);
//    }
//
//    @Override
//    public CompletableFuture<EntityFieldReadAccessor> process(EntityFieldReadAccessor inputEntity) {
//        EntityFieldReadWriteAccessor outputEntity = outputFactory.get();
//
//        // Fill in everything we already know.
////        enrichments.stream()
////                .flatMap(enrichment ->
////                        enrichment.getMappedCalculation()
////                                .getMapping()
////                                .getRightMapping()
////                                .getOutputSchema()
////                                .getFields()
////                                .stream())
////                // TODO replace by some generic copying, might be facilitated in a code generation
////                .forEach(field -> outputEntity.setValueOfField(field, inputEntity.getValueOfField(field)));
//
//        // Trigger the calculations of the enrichments.
//        // Keep track of the number of remaining enrichments, and of all the futures (in case we need to cancel some).
//    }
//}
