export interface Account {
  id: number;
  name: string;
  accountType: string;
  accountLines: [AccountLine];

}


export interface AccountLine {

  date: Date;
  sole: AmountOfMoney;

}

export interface AmountOfMoney{
	amount: number;
	currency: string
}

export interface AccountDateSum{
	sum: number;
	cuurency: string;
  year: number;
  month: number
}

export interface AccountDatePercentage{
	percentage: number;
  year: number;
  month: number
}

export interface AccountValue{
	value: number;
  year: number;
  month: number
}

export interface FileBase64{
	base64: string
}

export interface Market{
	indicePoint: number;
  year: number;
  month: number
}

export interface MarketPercentage{
	percentage: number;
  year: number;
  month: number
}

export interface MarketValue{
	value: number;
  year: number;
  month: number
}

export type AccountDataResult = AccountDateSum[] | AccountDatePercentage[];


export type MarketDataResult = Market[] | MarketPercentage[];

export interface CombinedData {
  dataAccount: AccountDataResult; // Peut être l'un ou l'autre
  dataMarket: Market; // Ajustez ce type si vous connaissez sa structure
}


