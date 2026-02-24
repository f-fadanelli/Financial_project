CREATE TABLE clients (
    id BIGSERIAL PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    full_name VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE TABLE portfolios (
    id BIGSERIAL PRIMARY KEY,
    client_id BIGINT NOT NULL,
    name VARCHAR(255) NOT NULL,
    base_currency VARCHAR(10) NOT NULL DEFAULT 'USD',
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),

    CONSTRAINT fk_portfolio_client
        FOREIGN KEY (client_id)
        REFERENCES clients(id)
        ON DELETE CASCADE
);

CREATE INDEX idx_portfolio_client_id
    ON portfolios(client_id);

CREATE TYPE trade_type AS ENUM ('BUY', 'SELL');

CREATE TABLE trades (
    id BIGSERIAL PRIMARY KEY,
    portfolio_id BIGINT NOT NULL,
    symbol VARCHAR(20) NOT NULL,
    trade_type trade_type NOT NULL,
    quantity NUMERIC(19,8) NOT NULL CHECK (quantity > 0),
    price NUMERIC(19,4) NOT NULL CHECK (price > 0),
    executed_at TIMESTAMP NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),

    CONSTRAINT fk_trade_portfolio
        FOREIGN KEY (portfolio_id)
        REFERENCES portfolios(id)
        ON DELETE CASCADE
);

CREATE INDEX idx_trades_portfolio
    ON trades(portfolio_id);

CREATE INDEX idx_trades_symbol
    ON trades(symbol);

CREATE INDEX idx_trades_portfolio_symbol
    ON trades(portfolio_id, symbol);

CREATE TABLE portfolio_positions (
    id BIGSERIAL PRIMARY KEY,
    portfolio_id BIGINT NOT NULL,
    symbol VARCHAR(20) NOT NULL,
    quantity NUMERIC(19,8) NOT NULL,
    average_buy_price NUMERIC(19,4) NOT NULL,
    updated_at TIMESTAMP NOT NULL DEFAULT NOW(),

    CONSTRAINT fk_position_portfolio
        FOREIGN KEY (portfolio_id)
        REFERENCES portfolios(id)
        ON DELETE CASCADE,

    CONSTRAINT uq_portfolio_symbol
        UNIQUE (portfolio_id, symbol)
);

CREATE INDEX idx_positions_portfolio
    ON portfolio_positions(portfolio_id);

CREATE TABLE stock_prices (
    id BIGSERIAL PRIMARY KEY,
    symbol VARCHAR(20) NOT NULL,
    price_date DATE NOT NULL,
    open_price NUMERIC(19,4) NOT NULL,
    high_price NUMERIC(19,4) NOT NULL,
    low_price NUMERIC(19,4) NOT NULL,
    close_price NUMERIC(19,4) NOT NULL,
    volume BIGINT NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),

    CONSTRAINT uq_symbol_date
        UNIQUE (symbol, price_date)
);

CREATE INDEX idx_stock_symbol_date
    ON stock_prices(symbol, price_date DESC);


CREATE TABLE realized_profits (
    id BIGSERIAL PRIMARY KEY,
    portfolio_id BIGINT NOT NULL,
    symbol VARCHAR(20) NOT NULL,
    trade_id BIGINT NOT NULL,
    realized_profit NUMERIC(19,4) NOT NULL,
    realized_at TIMESTAMP NOT NULL DEFAULT NOW(),

    CONSTRAINT fk_profit_portfolio
        FOREIGN KEY (portfolio_id)
        REFERENCES portfolios(id)
        ON DELETE CASCADE,

    CONSTRAINT fk_profit_trade
        FOREIGN KEY (trade_id)
        REFERENCES trades(id)
        ON DELETE CASCADE
);

CREATE TABLE portfolio_snapshots (
    id BIGSERIAL PRIMARY KEY,
    portfolio_id BIGINT NOT NULL,
    snapshot_date DATE NOT NULL,
    total_value NUMERIC(19,4) NOT NULL,
    total_cost_basis NUMERIC(19,4) NOT NULL,
    total_unrealized_pl NUMERIC(19,4) NOT NULL,
    total_realized_pl NUMERIC(19,4) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),

    CONSTRAINT fk_snapshot_portfolio
        FOREIGN KEY (portfolio_id)
        REFERENCES portfolios(id)
        ON DELETE CASCADE,

    CONSTRAINT uq_snapshot_portfolio_date
        UNIQUE (portfolio_id, snapshot_date)
);

CREATE INDEX idx_snapshots_portfolio_date
    ON portfolio_snapshots(portfolio_id, snapshot_date DESC);