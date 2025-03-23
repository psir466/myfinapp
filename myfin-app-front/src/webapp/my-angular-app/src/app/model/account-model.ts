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

export interface FileBase64{
	base64: string
}



