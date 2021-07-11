package data.mapper.pipeline;

import java.util.Optional;

public class ValueOrStrategy<NativeType> {

    private Optional<NativeType> value = Optional.empty();
    private Optional<Strategy> strategy = Optional.empty();

    public void setValue(NativeType value) {
        if (strategy.isPresent()) {
            throw new IllegalStateException("Setting value although strategy is already set!");
        }
        this.value = Optional.of(value);
    }

    public void useStrategy(Strategy strategy) {
        if (value.isPresent()) {
            throw new IllegalStateException("Setting strategy although value already set!");
        }
        this.strategy = Optional.of(strategy);
    }

    public Optional<Strategy> asStrategy() {
        return strategy;
    }

    public Optional<NativeType> asValue() {
        return value;
    }

}
