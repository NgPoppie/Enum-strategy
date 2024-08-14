enum TradeDay {
    MONDAY(TradeType.WEEKDAY),
    TUESDAY(TradeType.WEEKDAY),
    WEDNESDAY(TradeType.WEEKDAY),
    THURSDAY(TradeType.WEEKDAY),
    FRIDAY(TradeType.WEEKDAY),
    SATURDAY(TradeType.WEEKEND),
    SUNDAY(TradeType.WEEKEND);

    private final TradeType tradeType;

    TradeDay(TradeType tradeType) {
        this.tradeType = tradeType;
    }

    int amount(int minutes_traded, int lots) {
        return tradeType.pay(minutes_traded, lots);
    }

    enum TradeType {
        WEEKDAY {
            @Override
            int dailyPay(int tradesTaken, int profit) {
                return tradesTaken <= DAILY_TRADES ? 0 : (tradesTaken - DAILY_TRADES) * profit / 2;
            }
        },
        WEEKEND {
            @Override
            int dailyPay(int tradesTaken, int profit) {
                return tradesTaken * profit / 2;
            }
        };

        abstract int dailyPay(int trades, int profit);

        private static final int DAILY_TRADES = 7 * 70;

        int pay(int trades, int profit) {
            int baseProfits = trades * profit;
            return baseProfits + dailyPay(trades, profit);
        }
    }

    public static void main(String[] args) {
        for (TradeDay day : values()) {
            System.out.printf("%-10s%d%n", day, day.amount(7 * 70, 1));
        }
    }
}
